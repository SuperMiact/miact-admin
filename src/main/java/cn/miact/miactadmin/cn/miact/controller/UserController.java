package cn.miact.miactadmin.cn.miact.controller;

import cn.miact.miactadmin.cn.miact.entity.User;
import cn.miact.miactadmin.cn.miact.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : mw
 * @Classname :UserController
 * @createDate : 2022-05-31 15:28:13
 * @Description :
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/selectUsers")
    public List<User> selectUsers(){
        return userService.list();
    }

    @RequestMapping("/selectUserByLogin")
    public User selectUserByLogin(@RequestBody User user){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        QueryWrapper<User> eq = userQueryWrapper.eq("name", user.getName())
                .eq("password", user.getPassword());
        return userService.getOne(eq);
    }

}
