package cn.miact.miactadmin.cn.miact.service.impl;

import cn.miact.miactadmin.cn.miact.dao.IUserMapper;
import cn.miact.miactadmin.cn.miact.entity.User;
import cn.miact.miactadmin.cn.miact.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author : mw
 * @Classname :UserServiceImpl
 * @createDate : 2022-05-31 15:27:41
 * @Description :
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {

}
