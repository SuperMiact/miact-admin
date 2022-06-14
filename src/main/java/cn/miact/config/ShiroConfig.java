package cn.miact.config;

import cn.miact.filter.AuthFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : mawei
 * @Classname : ShiroConfig
 * @createDate : 2022-06-13 16:27:13
 * @Description :
 */
@Configuration
public class ShiroConfig {

    @Bean("userRm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean("securityManager")
    public SessionsSecurityManager securityManager(@Qualifier("userRm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }


    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //添加自定义的认证授权过滤器
        Map<String, Filter> filters = new HashMap<>();
        filters.put("auth", new AuthFilter());
        shiroFilter.setFilters(filters);

        //添加拦截路径
        Map<String, String> filterMap = new LinkedHashMap<String, String>();

        filterMap.put("/swagger/**", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/statics/**", "anon");
        filterMap.put("/api/users/login", "anon");
        filterMap.put("/api/users/logout", "anon");
        //除以上所有请求之外 均被自定义权限过滤拦截校验
        filterMap.put("/**", "auth");

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }

    /**
     * 配置以下bean借助SpringAOP扫描使用Shiro注解的类
     *
     * */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}

