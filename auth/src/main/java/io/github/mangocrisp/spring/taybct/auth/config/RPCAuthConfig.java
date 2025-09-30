package io.github.mangocrisp.spring.taybct.auth.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mangocrisp.spring.taybct.api.system.feign.IUserClient;
import io.github.mangocrisp.spring.taybct.auth.handle.AuthUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.config.AuthSecurityConfig;
import io.github.mangocrisp.spring.taybct.auth.security.filter.PasswordCheckFilter;
import io.github.mangocrisp.spring.taybct.auth.security.granter.customize.CustomizeTokenAuthenticationToken;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.handle.PasswordExceptionReporter;
import io.github.mangocrisp.spring.taybct.auth.security.pojo.OAuth2UserDetails;
import io.github.mangocrisp.spring.taybct.auth.security.support.OAuth2SqliteAuthorizationRowMapper;
import io.github.mangocrisp.spring.taybct.auth.security.support.OAuth2UserMixin;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

import java.util.List;

/**
 * @author xijieyin <br> 2023/1/3 14:11
 */
@AutoConfiguration(before = AuthSecurityConfig.class)
public class RPCAuthConfig {

    //    @Resource
    @DubboReference
    private IUserClient userClient;

    @Bean
    @Order(0)
    public IUserDetailsHandle feignUserDetailsHandle() {
        return new AuthUserDetailsHandle(userClient);
    }

    /**
     * 密码验证过滤器,密码验证失败一定次数就不给过了
     *
     * @param redisTemplate redis 操作
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<PasswordCheckFilter> passwordCheckFilter(RedisTemplate<String, Integer> redisTemplate) {
        FilterRegistrationBean<PasswordCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PasswordCheckFilter(redisTemplate));
        registrationBean.addUrlPatterns("/oauth/login");
        registrationBean.setName("PasswordCheckFilter");
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 3);
        return registrationBean;
    }

    /**
     * 全局异常捕获记录器
     *
     * @return IGlobalExceptionReporter
     */
    @Bean
    public IGlobalExceptionReporter globalExceptionReporter(RedisTemplate<String, Integer> redisTemplate) {
        return new PasswordExceptionReporter(redisTemplate);
    }


    /**
     * 因为用到了 SQLite，类型转换会有问题，这里做一下类型解析器
     * TODO 如果需要兼容 SQLite 就需要加 @Bean 来把解析器注册到 spring 容器中
     *
     * @param jdbcTemplate               数据源
     * @param registeredClientRepository 注册
     * @return OAuth2AuthorizationService
     */
    public OAuth2AuthorizationService authorizationService(JdbcTemplate jdbcTemplate
            , RegisteredClientRepository registeredClientRepository) {
        JdbcOAuth2AuthorizationService service = new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
        OAuth2SqliteAuthorizationRowMapper authorizationRowMapper =
                new OAuth2SqliteAuthorizationRowMapper(jdbcTemplate, registeredClientRepository);
        authorizationRowMapper.setLobHandler(new DefaultLobHandler());
        authorizationRowMapper.setObjectMapper(objectMapper());
        service.setAuthorizationRowMapper(authorizationRowMapper);
        return service;
    }

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(classLoader);
        objectMapper.registerModules(securityModules);
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        //放入自定义的user类
        objectMapper.addMixIn(OAuth2UserDetails.class, OAuth2UserMixin.class);
        objectMapper.addMixIn(CustomizeTokenAuthenticationToken.class, OAuth2UserMixin.class);
        return objectMapper;
    }

}
