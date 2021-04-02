package com.phoenixhell.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author phoenixhell
 * @create 2021/1/22 0022-下午 1:03
 */
@Configuration
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

//yaml里面配置了会自动注入
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DruidDataSource druidDataSource() throws SQLException {
//        return new DruidDataSource();
//    }

    //选择建表数据源 接口就行 会自己找配置的实现类
    @Resource
    private DataSource dataSource;

    // 注册 JdbcTokenRepositoryImpl
    //public class JdbcTokenRepositoryImpl extends JdbcDaoSupport implements PersistentTokenRepository
    @Bean
    public PersistentTokenRepository persistentTokenRepository() throws SQLException {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动建表 第一次运行需要
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index");
        http.exceptionHandling().accessDeniedPage("/forbidden.html");
        http.formLogin()
                .loginPage("/login.html")  //自定义页面
                .loginProcessingUrl("/login") //form里面 action处理表单提交地址controller sping 提供
                .defaultSuccessUrl("/index")
                .failureUrl("/failAuth")//The URL to send users if authentication fails.
                .usernameParameter("username")//form表单中input的name名字 不该的话默认是username
                .passwordParameter("password")//form表单中input的name名字 不该的话默认是password
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/index", "/", "/login") //不需要登登陆认证的页面
                .permitAll()
                //设置访问路径需要的权限
                .antMatchers("/admin").hasAuthority("admin")
                .antMatchers("/auth").hasAuthority("auth")
                .antMatchers("/message").hasAnyAuthority("admin,auth")
                .antMatchers("/role").hasRole("role")
                .antMatchers("/roles").hasAnyRole("roles,otherRole")
                .anyRequest().authenticated()//所有url 认证用户都可以访问
                .and()
                //开启rememberMe 指定token数据库  由JdbcTokenRepositoryImpl 创建
                .rememberMe()
                .rememberMeParameter("remember-me")//form表单中input的remember-me名字 不该的话默认是remember-me
                .tokenRepository(persistentTokenRepository())//指定token表
                .tokenValiditySeconds(60)//token 有效时长 单位为60秒
                //通过myUserDetailsServiceImpl查询出user来和token表配对
                .userDetailsService(userDetailsService);
    }

    //spring security 并不知道你要用什么加密工具必须指定并注入到容器中
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
