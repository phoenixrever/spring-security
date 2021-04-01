package com.phoenixhell.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/4/1 0001-下午 3:03
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
public class SpringSecurity8111 {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity8111.class, args);
    }
}
