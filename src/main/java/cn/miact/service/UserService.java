package cn.miact.service;



import cn.miact.domain.common.PageQuery;
import cn.miact.domain.common.PageResult;
import cn.miact.domain.dto.UserDTO;
import cn.miact.domain.dto.UserQueryDTO;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 新增
     */
    int save(UserDTO userDTO);

    /**
     * 更新
     */
    int update(Long id,UserDTO userDTO);

    /**
     * 删除
     */
    int delete(Long id);

    UserDTO queryLoginUser(UserQueryDTO loginQuery);

    /**
     * 分页查询
     */
    PageResult<List<UserDTO>> query(PageQuery<UserQueryDTO> pageQuery);
}
