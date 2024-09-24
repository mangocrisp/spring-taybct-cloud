package io.github.mangocrisp.spring.taybct.admin.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.DispatcherType;
import java.util.UUID;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

/**
 * @author xijieyin
 * <br> 2021/12/3 9:15
 * 鉴权配置
 */
@AutoConfiguration
@EnableWebSecurity
public class WebSecurityConfigurer {

    /**
     * 是否需要鉴权
     */
    @Value("${spring.boot.admin.auth.enable}")
    private boolean needAuth = true;
    /**
     * 用户名
     */
    @Value("${spring.boot.admin.auth.username}")
    private String username = "taybct";
    /**
     * 密码
     */
    @Value("${spring.boot.admin.auth.password}")
    private String password = "123654";

    private final AdminServerProperties adminServer;

    public WebSecurityConfigurer(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.path("/"));

        http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // 	Grants public access to all static assets and the login page.
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/assets/**"))).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/variables.css"))).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/actuator/info"))).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/actuator/health"))).permitAll()
                        .requestMatchers(new AntPathRequestMatcher(this.adminServer.path("/login"))).permitAll()
                        // https://github.com/spring-projects/spring-security/issues/11027
                        .dispatcherTypeMatchers(DispatcherType.ASYNC).permitAll()
                        // Every other request must be authenticated.
                        .anyRequest().authenticated())
                // Configures login and logout.
                .formLogin((formLogin) -> formLogin.loginPage(this.adminServer.path("/login"))
                        .successHandler(successHandler))
                .logout((logout) -> logout.logoutUrl(this.adminServer.path("/logout")))
                // Enables HTTP-Basic support. This is needed for the Spring Boot Admin Client to register.
                .httpBasic(Customizer.withDefaults());
        // Enables CSRF-Protection using Cookies
        http
//                .addFilterAfter(new CustomCsrfFilter(), BasicAuthenticationFilter.class)
                .csrf((csrf) ->
                        csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/actuator/**"))
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                .ignoringRequestMatchers(
                                        // Disables CSRF-Protection for the endpoint the Spring Boot Admin Client uses to (de-)register
                                        new AntPathRequestMatcher(this.adminServer.path("/instances"), POST.toString()),
                                        new AntPathRequestMatcher(this.adminServer.path("/instances/*"), DELETE.toString()),
                                        // Disables CSRF-Protection for the actuator endpoints.
                                        new AntPathRequestMatcher(this.adminServer.path("/actuator/**"))
                                ));

        http.rememberMe((rememberMe) -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));

        return http.build();

    }

    // Required to provide UserDetailsService for "remember functionality"
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername(username).password(passwordEncoder.encode(password)).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
