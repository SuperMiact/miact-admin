package cn.miact.config;

import cn.miact.config.AuthToken;
import cn.miact.domain.entity.UserDO;
import cn.miact.service.UserService;
import cn.miact.util.RedisUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author : mawei
 * @Classname : UserRelam
 * @createDate : 2022-06-13 16:29:15
 * @Description :
 */

@Component
public class UserRealm extends AuthorizingRealm {

//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private SysMenuService sysMenuService;
//
//    @Autowired
//    private RedisUtils redisUtils;

    @Autowired
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AuthToken;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        //获取前端传来的token
        String accessToken = (String) authToken.getPrincipal();
        String username = (String) redisUtils.get("SYS_USER"+accessToken);
        if (username == null)
            throw new IncorrectCredentialsException("token失效，请重新登录");
        UserDO user = userService.findByUsername(username);
        if (user == null)
            throw new UnknownAccountException("用户不存在!");
        if (user.getStatus() == 0)
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        //传入user以及获取到的token实现自动认证
        return new SimpleAuthenticationInfo(user, accessToken, getName());
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDO user = (UserDO) principals.getPrimaryPrincipal();
//        Long userId = user.getId();
        //获取用户权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions(getUserPermissions(userId));
        return info;
    }

//    //获取用户权限
//    private Set<String> getUserPermissions(long userId) {
//        List<String> permsList;
//        //管理员权限列表
//        if (userId == CommonConstant.SUPER_ADMIN) {
//            List<SysMenu> menuList = sysMenuService.selectAll();
//            permsList = new ArrayList<>(menuList.size());
//            for (SysMenu menu : menuList) {
//                permsList.add(menu.getPerms());
//            }
//        } else {
//            permsList = sysUserService.queryAllPerms(userId);
//        }
//
//        //用户权限列表
//        Set<String> permsSet = new HashSet<>();
//        for (String perms : permsList) {
//            if (StringUtils.isBlank(perms))
//                continue;
//            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
//        }
//        return permsSet;
//    }
}

