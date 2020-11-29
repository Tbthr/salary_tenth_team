package com.salary.security;

import com.salary.service.UserService;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security全局注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserService userService;
    @Resource
    private UserAuthenticationFailureHandler userAuthenticationFailureHandler;
    @Resource
    private UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;
    @Resource
    private MyAccessDecisionManager myAccessDecisionManager;
    @Resource
    private MySecurityMetadataSource mySecurityMetadataSource;
    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Resource
    private TokenAuthenticationFilter tokenAuthenticationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 允许匿名的url
        web.ignoring().antMatchers("/forget", "/sendmail");
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
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic().and()//认证
                // 设置登陆页
                .formLogin()
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

    @Bean
    // security 加密组件
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
