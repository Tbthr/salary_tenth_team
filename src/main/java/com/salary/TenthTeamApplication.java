package com.salary;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.salary.mapper")
public class TenthTeamApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenthTeamApplication.class, args);
    }

}
