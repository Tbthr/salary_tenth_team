package com.salary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@SpringBootApplication
@MapperScan("com.salary.mapper")
public class TenthTeamApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenthTeamApplication.class, args);
    }

    @Bean
    // 跨域
    public CorsConfigurationSource corsConfigurationSource() {
        return httpServletRequest -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.addAllowedHeader("*");
            cfg.addAllowedMethod("*");
            cfg.addAllowedOrigin("*");
            cfg.setAllowCredentials(true);
            cfg.checkOrigin("*");
            return cfg;
        };
    }

    @Bean
    // 返回前端 object to string 组件
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
