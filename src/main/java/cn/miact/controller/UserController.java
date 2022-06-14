package cn.miact.controller;


import cn.miact.domain.common.PageQuery;
import cn.miact.domain.common.PageResult;
import cn.miact.domain.common.ResponseResult;
import cn.miact.domain.dto.UserDTO;
import cn.miact.domain.dto.UserQueryDTO;
import cn.miact.domain.entity.UserDO;
import cn.miact.exception.ErrorCodeEnum;
import cn.miact.domain.vo.UserVO;
import cn.miact.service.ExcelExportService;
import cn.miact.service.UserService;
import cn.miact.util.InsertValidationGroup;
import cn.miact.util.TokenGenerator;
import cn.miact.util.UpdateValidationGroup;
import cn.miact.util.RedisUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/users")
@Validated
@Slf4j
@Api(
        value = "用户管理Controller",
        protocols = "http,https",
        hidden = false
)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelExportService excelExportService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * POST /api/users  UserDTO传输类来包装我们额请求信息
     * 新增用户
     */
    @CacheEvict(cacheNames = "users-cache",allEntries = true)
    @PostMapping
    public ResponseResult save(
            @Validated(InsertValidationGroup.class)
            @RequestBody UserDTO userDto){

        int save = userService.save(userDto);

        if (save==1){
            return ResponseResult.success("新增成功");
        } else {
            return ResponseResult.failure(ErrorCodeEnum.INSERT_FAILURE);
        }


    }

    /**
     * 更新用户信息
     * PUT /api/users/{id} UserDTO userDTO
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation(
            value = "更新用户信息",
            notes = "备注说明信息",
            response = ResponseResult.class,
            httpMethod = "PUT"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "参数说明，用户主键",
                    required = true,
                    paramType = "path",
                    dataType = "Long",
                    example = "12345"
            ),
            @ApiImplicitParam(
                    name = "userDto",
                    value = "用户信息",
                    required = true,
                    paramType = "body",
                    dataType = "UserDTO",
                    dataTypeClass = UserDTO.class
            )
    })
    @ApiResponses({
            @ApiResponse(code = 0000,message = "操作成功"),
            @ApiResponse(code = 3004,message = "更新失败")
    })
    public ResponseResult update(@NotNull @PathVariable("id") Long id,
                                 @Validated(UpdateValidationGroup.class)
                                 @RequestBody UserDTO userDto){

        int update = userService.update(id, userDto);

        if (update == 1){
            return ResponseResult.success("更新成功");
        }else{
            return ResponseResult.failure(ErrorCodeEnum.UPDATE_FAILURE);
        }


    }

    /**
     * 删除用户信息
     * DELETE /api/users/{id}
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult delete(@NotNull(message = "用户ID不能为空！")
                                 @PathVariable("id") Long id){
        int delete = userService.delete(id);

        if (delete == 1){
            return ResponseResult.success("删除成功！");
        }else {
            return ResponseResult.failure(ErrorCodeEnum.DELETE_FAILURE);
        }
    }

    /**
     * 查询用户信息
     * GET
     * @return
     */
    @Cacheable(cacheNames = "users-cache")
    @GetMapping
    public ResponseResult<PageResult> query(@NotNull Integer pageNo,
                                            @NotNull Integer pageSize,
                                            @Validated UserQueryDTO query){
        log.info("未使用缓存！");
        // 构造查询条件
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(query);

        // 查询
        PageResult<List<UserDTO>> pageResult = userService.query(pageQuery);

        // 实体转换
        List<UserVO> userVOList = Optional.ofNullable(pageResult.getData())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(userDTO -> {
                    UserVO userVO = new UserVO();

                    BeanUtils.copyProperties(userDTO, userVO);
                    // 对特殊字段做处理
                    userVO.setPassword("*********");
                    if (!StringUtils.isEmpty(userDTO.getPhone())){
                        userVO.setPhone(userDTO.getPhone()
                                .replaceAll("(\\d{3})\\d{4}(\\d{4})"
                                        , "$1****$2"));
                    }

                    return userVO;
                })
                .collect(Collectors.toList());

        //封装返回结果
        PageResult<List<UserVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult,result);
        result.setData(userVOList);

        return ResponseResult.success(result);
    }

    @RequestMapping("/login")
    @ApiOperation(value = "登录",notes = "登录")
    public ResponseResult login(@RequestBody UserQueryDTO param)  {

        UserDO user = userService.findByUsername(param.getUsername());
        String password = param.getPassword();

        if (user == null){
            return ResponseResult.failure("账号错误");
        }

        if (!password.equals(user.getPassword())){
            return ResponseResult.failure("密码错误");
        }

        String token = TokenGenerator.generateValue();
        redisUtils.set("SYS_USER" + token, param.getUsername(), (long) (3600 * 8));
        return ResponseResult.success(token,"登录成功！");
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "退出", notes = "退出")
    public ResponseResult logout(@RequestParam("token") String token) {
        redisUtils.remove("SYS_USER" + token);
        return ResponseResult.success(null,"登出成功！");
    }

    /**
     * 用户数据导出
     * @param query
     * @param filename
     * @return
     */
    @GetMapping("/export")
    public ResponseResult<Boolean> export(@Validated UserQueryDTO query,
                                          @NotNull String filename){

        log.info("接收到导出请求！filename = {}",filename);

        // 数据导出
        excelExportService.export(query,filename);

//        excelExportService.asyncExport(query,filename);

        return ResponseResult.success(Boolean.TRUE);
    }

}
