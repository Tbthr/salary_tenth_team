package com.salary.Security;

import com.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security全局注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthenticationFailureHandler userAuthenticationFailureHandler;
    @Autowired
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;
    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/forget","/sendmail");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(myAccessDecisionManager);
                        object.setSecurityMetadataSource(mySecurityMetadataSource);
                        return object;
                    }
                })
                // 如果有允许匿名的url，填在下面
//                .antMatchers("/forget","/sendmail").permitAll()//允许所有人
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic().and()//认证
                // 设置登陆页
                .formLogin()
//                .loginPage("/login")
                .loginProcessingUrl("/login")
                // 设置登陆成功页
                .successHandler(userAuthenticationSuccessHandler)
                .failureHandler(userAuthenticationFailureHandler)
                //.defaultSuccessUrl("/").permitAll()
                // 自定义登陆用户名和密码参数，默认为username和password
                .usernameParameter("id")
                .passwordParameter("psd")
                .and()//跨域
                .cors()
                .and()
                .logout().permitAll();

        // 关闭CSRF跨域
        http.csrf().disable().exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
    }

//    @Bean
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
//        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//        handler.setPermissionEvaluator(new UserPermissionEvaluator());
//        return handler;
//    }
        @Bean
        CorsConfigurationSource corsConfigurationSource(){
            return httpServletRequest -> {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.addAllowedHeader("*");
                cfg.addAllowedMethod("*");
                cfg.addAllowedOrigin("http://localhost:8080");
                cfg.setAllowCredentials(true);
                cfg.checkOrigin("http://localhost:8080");
                return cfg;
            };
        }
}
