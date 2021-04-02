package com.phoenixhell.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/4/1 0001-下午 3:03
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.phoenixhell.security.mapper")
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)//开启配置权限注解
public class SpringSecurity8111 {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity8111.class, args);
    }
}
