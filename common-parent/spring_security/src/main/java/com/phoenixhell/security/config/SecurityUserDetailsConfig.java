package com.phoenixhell.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author phoenixhell
 * @create 2021/1/22 0022-下午 1:03
 */
//@Configuration
public class SecurityUserDetailsConfig extends WebSecurityConfigurerAdapter {
    //    @Qualifier("myUserDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //重写了此方法会覆盖默认登陆页面
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();  //注销
        //直接指定页面的必须在static template只能是controller路径
        http.exceptionHandling().accessDeniedPage("/error/forbidden.html");
        http.formLogin()
                    .loginPage("/login.html")//用户未登陆时候,访问任何资源都跳转到该路径,登陆静态页面
                    .loginProcessingUrl("/login")///登陆表单form里面 action处理表单提交地址controller spring 提供
                    .defaultSuccessUrl("/index")//登陆成功跳转路径
                    .usernameParameter("username")//form表单中input的name名字 不该的话默认是username
                    .passwordParameter("password")//form表单中input的name名字 不该的话默认是password
                    .permitAll()
                    .and()
                .authorizeRequests()//需要登陆路径request
                    .antMatchers("/", "/login", "/hello")//不需要登陆验证就可以访问的路径
                    .permitAll()
                    .antMatchers("/admin")
                    .hasAuthority("admin")//需要admin权限才能访问
                    .antMatchers("MultiAuthority")
                    .permitAll()
                    .antMatchers("/role")
        //role 和 权限基本一样 role可以有多个权限  permissions
        //ROLE_  hasRole会自动加入前缀
        //AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_role");
                    .hasRole("admin")
                    .antMatchers("/anyRole")
                    .hasAnyRole("admin,customer")//任意一个权限都可以访问
                    .anyRequest()
                    //Specify that URLs are allowed by any authenticated user
                    //指定所有url 认证用户都可以访问
                    .authenticated()
                .and()
                    .csrf().disable();//关闭crsf跨域攻击

    }

    //spring security 并不知道你要用什么加密工具必须指定并注入到容器中
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
