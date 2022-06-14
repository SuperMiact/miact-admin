package cn.miact.filter;

import cn.miact.config.AuthToken;
import cn.miact.domain.common.ResponseResult;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : mawei
 * @Classname : AuthFilter
 * @createDate : 2022-06-13 17:30:54
 * @Description :
 */
public class AuthFilter extends AuthenticatingFilter {

    Gson gson = new Gson();

    //生成自定义token
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token = getUserToken((HttpServletRequest) request);
        return new AuthToken(token);
    }

    // isAccessAllowed是判断哪些请求可以被shiro处理，哪些不可以被shiro处理。
    // 判断这次request请求是不是options请求。如果不是,就需要被shiro处理。
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return ((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name());
    }


    //
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = getUserToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(gson.toJson(ResponseResult.failure("请先登录")));
            return false;
        }
        return executeLogin(request, response);
    }


    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpResponse.setCharacterEncoding("UTF-8");
        try {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            httpResponse.getWriter().write(gson.toJson(gson.toJson(ResponseResult.failure("登录凭证已失效，请重新登录"))));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    //如果header中不存在token，则从参数中获取token
    private String getUserToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token))
            token = request.getParameter("token");
        return token;
    }
}

