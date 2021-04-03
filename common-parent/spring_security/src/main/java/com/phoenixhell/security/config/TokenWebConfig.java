package com.phoenixhell.security.config;

import com.phoenixhell.security.filter.TokenAuthFilter;
import com.phoenixhell.security.filter.TokenLoginFilter;
import com.phoenixhell.security.security.DefaultPasswordEncoder;
import com.phoenixhell.security.security.TokenLogoutHandler;
import com.phoenixhell.security.security.TokenManager;
import com.phoenixhell.security.security.UnAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;
import javax.validation.executable.ValidateOnExecution;

/**
 * @author phoenixhell
 * @since 2021/4/3 0003-下午 3:00
 */
@Configuration
public class TokenWebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Resource
    private AuthenticationManager authenticationManager;

    //只在配置那边用一次就不用注入容器了 直接new吧
    //角色的权限路径限制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(new UnAuthEntryPoint())//设置没有权限访问的处理类
                .and().csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")
                .addLogoutHandler(new TokenLogoutHandler())
                .and()
                .addFilter(new TokenLoginFilter())
                .addFilter(new TokenAuthFilter(authenticationManager));

    }
    //一般用于配置全局的某些通用事物，例如静态资源等
    //ignore 不用认证可以反问的路径
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new DefaultPasswordEncoder());
    }
}
