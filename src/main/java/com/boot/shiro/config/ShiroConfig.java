package com.boot.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.boot.shiro.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
public class ShiroConfig {
    /**
     * @description
     * 		把shiro的生命周期交给spring进行托管
     * @company 北京尚学堂
     * @author Seven Lee
     * @return LifecycleBeanPostProcessor
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor processor = new LifecycleBeanPostProcessor();
        return processor;
    }

    /**
     * @description
     * 		密码加密的配置
     * @company 北京尚学堂
     * @author Seven Lee
     * @return HashedCredentialsMatcher
     */
//    @Bean(name = "credentialsMatcher")
////    public HashedCredentialsMatcher hashedCredentialsMatcher() {
////        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
////        // 设置密码的加密方式
////        matcher.setHashAlgorithmName("MD5");
////        // 设置密码的加密次数
////        matcher.setHashIterations(1024);
////        // 设置是否进行编码(true:UTF-8,false:项目默认编码)
////        matcher.setStoredCredentialsHexEncoded(true);
////        return matcher;
////    }

    /**
     * @description
     * 		自定义的shiroRealm(认证，授权)
     * 		@DependsOn依赖于某一个bean:lifecycleBeanPostProcessor
     * @company 北京尚学堂
     * @author Seven Lee
     * @return ShiroRealm
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    /**
     * @description
     * 		缓存管理器，也需要依赖于lifecycleBeanPostProcessor
     * @company 北京尚学堂
     * @author Seven Lee
     * @return EhCacheManager
     */
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager() {
        System.out.println("缓存初始化");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * @description
     * 		安全管理器
     * @company 北京尚学堂
     * @author Seven Lee
     * @return DefaultWebSecurityManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * @description
     * 		shiroFilter
     * @company 北京尚学堂
     * @author Seven Lee
     * @return ShiroFilterFactoryBean
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        // 1.创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager());
        // 2.实现filterChainDefinitions
        // 2.1.创建LinkedHashMap
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/favicon.ico","anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/toReg", "anon");
        filterChainDefinitionMap.put("/reg", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        // 3.实现LoginUrl,SuccessUrl,UnauthorizedUrl
        filter.setLoginUrl("/login");
        filter.setSuccessUrl("/");
        filter.setUnauthorizedUrl("/404");

        filter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filter;
    }

    /**
     * @description
     * 		启动代理类
     * @company 北京尚学堂
     * @author Seven Lee
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * @description
     * 		创建Authorization对象
     * @company 北京尚学堂
     * @author Seven Lee
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager());
        return aASA;
    }

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


}
