package cn.miact.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author : mawei
 * @Classname : AuthToken
 * @createDate : 2022-06-13 17:33:58
 * @Description :
 */
public class AuthToken implements AuthenticationToken {
    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

