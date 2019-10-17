package com.hat.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

//把ShiroConfig类标注为配置类
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")//获取application.yml的spring.redis.hosts属性的值
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private Integer database;

    /**
     * 配置生命周期处理器
     * @return
     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }

    /**
     * shiro的过滤器方法,该方法用来指定一些过滤规则
     *
     * @Qualifier注解：注入名为securityManager的bean
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager){
        //创建ShiroFilterFactoryBean实例
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器(SecurityManager),必须要设置，不然在初始化阶段会抛出异常
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //用户未认证通过时跳转的页面，不指定时默认是 /login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login");
        //用户认证通过时跳转到的页面，不指定时默认是 /
        shiroFilterFactoryBean.setSuccessUrl("/main");
        //用户认证通过，但是无授权时跳转到的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        //路由过滤器链，注意是LinkedHashMap，因为要按照顺序来指定规则
        Map<String,String> chain = new LinkedHashMap<>();
        //Shiro提供了11种过滤规则,以下是几种常用过滤器：
        //      anon：匿名，例："/login","anon"；/login路由所有用户都可以访问
        //      authc：认证，例："/main/*","authc"；/main及其所有子路由需要用户认证成功后才可以访问
        //      Perms：权限，例："/space","Perms[perm1]"; /space路由需要有user权限才可以访问，可以多个，用逗号隔开
        //      roles：角色，例："/role","roles[role1，role2]"; /role路由需要同时为role1、role2角色时才可以访问
        //      user：如果使用了“记住我”功能的用户访问/**时走这个过滤器
        //      logout：注销，访问/logout地址就退出登录，删除该用户的存在Subject的信息,并且页面回到setLoginUrl()设置的路径
        chain.put("/login","anon");
        chain.put("/main","authc");
        chain.put("/user","roles[user]");
        chain.put("/manager","roles[manager]");
        chain.put("/perm_a","perms[a,b]");
        chain.put("/perm_b","perms[c,d]");
        chain.put("/logout","logout");
        //这个过滤规则必须放在最后，意思是除了/login路径外的所有路径都需要用户认证通过才可以访问
        chain.put("/**","user");
        //设置过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chain);
        return shiroFilterFactoryBean;
    }

    /**
     * shiro的安全管理器方法
     * @param customRealm 自定义的realm
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("customRealm") CustomRealm customRealm){
        //实例化DefaultWebSecurityManager，记住是有Web的这个安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //SecurityManager需要设置自定义的Realm
        securityManager.setRealm(customRealm);

        //设置session管理器，保存session缓存到redis
        securityManager.setSessionManager(sessionManager());
        //设置redis缓存管理器，保存认证和授权信息到redis
        securityManager.setCacheManager(redisCacheManager());

        return securityManager;
    }

    /**
     * 实例化自定义的Realm并且把它交给spring管理供SecurityManager使用
     * @return
     */
    @Bean(name = "customRealm")
    public CustomRealm customRealm(){
        CustomRealm realm = new CustomRealm();

//        realm.setCachingEnabled(true);  //启动缓存，默认true
//        realm.setAuthorizationCachingEnabled(true); //启动授权缓存，默认true
        //启动用户认证缓存，默认false。注意：如果开启认证缓存的话在使用密码加密时会抛出序列化异常
//        realm.setAuthenticationCachingEnabled(true);
        //设置用户认证的缓存名称
        realm.setAuthenticationCacheName("authenticationCache");
        //设置角色授权和权限授权的缓存名称
        realm.setAuthorizationCacheName("authorizationCache");

        //设置使用密码加密算法
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /**
     * redis管理器，用来设置连接redis的属性配置
     * @return
     */
    @Bean
    public RedisManager redisManager(){
        //实例化redis管理器
        RedisManager redisManager = new RedisManager();
        //默认是127.0.0.1：6379，注意这个host包括端口号
        redisManager.setHost(host+":"+port);
        //连接redis的密码
        redisManager.setPassword(password);
        //设置redis连接超时时间，默认2000ms
        redisManager.setTimeout(0);
        //设置使用第几个数据库，默认0
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%++"+database);
        redisManager.setDatabase(1);
        return redisManager;
    }

    /**
     * redis缓存管理器，存放认证和授权信息的缓存，如role、perm和登录等
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager(){
        //实例化RedisCacheManager
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        //设置redis管理器
        redisCacheManager.setRedisManager(redisManager());
        //设置主体的id名称，这个值必须是唯一的，默认是id
        redisCacheManager.setPrincipalIdFieldName("username");
        //设置缓存保存时间，默认1800秒，单位秒
        redisCacheManager.setExpire(60 * 60 * 24); //1天过期
        //设置缓存的key的前缀，默认"shiro:cache:"
//        redisCacheManager.setKeyPrefix("shiro:cache:");
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO是使用redis为会话内容(session)进行持久化
     * 保存session的缓存，该缓存在每次访问时都会刷新过期时间
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        //实例化RedisSessionDAO
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        //设置redis管理器
        redisSessionDAO.setRedisManager(redisManager());
        //session保存的时间，最好设置成比默认session保存的时间长，默认是30分钟，单位秒
        redisSessionDAO.setExpire(60 * 60 * 24); //1天过期
        //设置session缓存的key前缀，默认"shiro:session:"
//        redisSessionDAO.setKeyPrefix("shiro:session:");
        //sessionId生成器，默认是JavaUuidSessionIdGenerator。
//        redisSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return redisSessionDAO;
    }

    /**
     * session管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        //设置session持久类
        defaultWebSessionManager.setSessionDAO(redisSessionDAO());
        //不显示url后面的jsession，默认true
        defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
        //设置全局session过期时间，默认1800秒，单位毫秒
        defaultWebSessionManager.setGlobalSessionTimeout(60 * 60 * 24 *1000); //1天过期
        return defaultWebSessionManager;
    }


    /**
     * 加密密码匹配器
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //使用md5算法加密
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密的迭代次数，如:md5(md5())
        credentialsMatcher.setHashIterations(2);
        //保存密码使用hex编码，默认true，使用base64则设为false
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     * 以下两个bean开启权限和角色注解支持
     * DefaultAdvisorAutoProxyCreator与AuthorizationAttributeSourceAdvisor都要配置才生效
     * 在controller类中使用@RequiresPermissions("pernm_a")
     *                  和@RequiresRoles("role")
     * @param
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 捕获异常并设置显示指定页面的内容
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.AuthorizationException","/unauthorized");
        resolver.setExceptionMappings(properties);
        return resolver;
    }

}
