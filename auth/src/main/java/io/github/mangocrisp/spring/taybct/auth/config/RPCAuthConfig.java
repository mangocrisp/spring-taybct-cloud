package io.github.mangocrisp.spring.taybct.auth.config;

import io.github.mangocrisp.spring.taybct.api.system.feign.IUserClient;
import io.github.mangocrisp.spring.taybct.auth.handle.AuthUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.config.AuthSecurityConfig;
import io.github.mangocrisp.spring.taybct.auth.security.filter.PasswordCheckFilter;
import io.github.mangocrisp.spring.taybct.auth.security.handle.IUserDetailsHandle;
import io.github.mangocrisp.spring.taybct.auth.security.handle.PasswordExceptionReporter;
import io.github.mangocrisp.spring.taybct.tool.core.exception.handler.IGlobalExceptionReporter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

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

}
