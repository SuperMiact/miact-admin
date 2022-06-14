package cn.miact.service.impl;

import cn.miact.domain.common.PageQuery;
import cn.miact.domain.common.PageResult;
import cn.miact.domain.dto.UserDTO;
import cn.miact.domain.dto.UserQueryDTO;
import cn.miact.domain.entity.UserDO;
import cn.miact.mapper.UserMapper;
import cn.miact.service.UserService;
import cn.miact.util.ValidatorUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(UserDTO userDTO) {
        UserDO userDO = new UserDO();

        //TODO 浅拷贝  属性名相同才能拷贝
        //把userDTO中的属性拷贝到userDO的属性上
        BeanUtils.copyProperties(userDTO,userDO);

        return userMapper.insert(userDO);
    }

    @Override
    public int update(Long id, UserDTO userDTO) {
        UserDO userDO = new UserDO();

        BeanUtils.copyProperties(userDTO,userDO);

        //保证更新的操作一定是操作这个ID的数据
        userDO.setId(id);


        return userMapper.updateById(userDO);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public UserDO findByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<UserDO>().eq("username",username));
    }

    @Override
    public PageResult<List<UserDTO>> query(PageQuery<UserQueryDTO> pageQuery) {

        //  手动校验功能
        ValidatorUtils.validate(pageQuery);

        //  参数构造
        Page page = new Page(pageQuery.getPageNo(),
                pageQuery.getPageSize());

        UserDO query = new UserDO();
        BeanUtils.copyProperties(pageQuery.getQuery(),query);
        //  TODO 如果属性不一致，需要做特殊处理
        QueryWrapper queryWrapper = new QueryWrapper(query);

        //  查询
        IPage<UserDO> userDOIPage = userMapper.selectPage(page, queryWrapper);

        //  结果解析
        PageResult pageResult = new PageResult();
        pageResult.setPageNo((int) userDOIPage.getCurrent());
        pageResult.setPageSize((int) userDOIPage.getSize());
        pageResult.setTotal(userDOIPage.getTotal());
        pageResult.setPageNum(userDOIPage.getPages());

        //  对数据进行转换
        List<UserDTO> userDTOList = Optional
                .ofNullable(userDOIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(userDO -> {
                    UserDTO userDTO = new UserDTO();
                    // userDO中的创建时间，创建人等都没有拷到userDTO中，DTO只保留了它那部分需要用到的信息
                    BeanUtils.copyProperties(userDO, userDTO);
                    return userDTO;
                })
                .collect(Collectors.toList());

        pageResult.setData(userDTOList);

        return pageResult;
    }
}
