//package com.phoenixhell.security.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.spring.demo1.service.serviceImpl.MyPasswordEncoder;
//import com.spring.demo1.service.serviceImpl.MyUserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import java.sql.SQLException;
//
///**
// * @author phoenixhell
// * @create 2021/1/22 0022-下午 1:03
// */
////@Configuration
//public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
////        String password = bCryptPasswordEncoder.encode("1234");
////        auth.inMemoryAuthentication().withUser("admin").password(password).roles("admin");
////    }
//
//    @Autowired
//    private MyUserDetailsServiceImpl myUserDetailsServiceImpl;
//
////    @Bean
////    @ConfigurationProperties(prefix = "spring.datasource")
////    public DruidDataSource druidDataSource() throws SQLException {
////        return new DruidDataSource();
////    }
//    @Autowired
//    private DruidDataSource druidDataSource;
//    //private JdbcTemplate jdbcTemplate;
//
//    // 注册 JdbcTokenRepositoryImpl
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() throws SQLException {
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(druidDataSource);
////        jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
//    }
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        //貌似只起到了个指定加密器的作用 不重写一样ok
//        auth.userDetailsService(myUserDetailsServiceImpl).passwordEncoder(passwordEncoder());
//    }
////
////
//    @Bean
//    PasswordEncoder passwordEncoder(){
////        return new BCryptPasswordEncoder();
//        return new MyPasswordEncoder();
//    }
////
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable();
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index");
//        http.exceptionHandling().accessDeniedPage("/forbidden.html");
//        http.formLogin()
//                .loginPage("/login")  //自定义页面
//                .loginProcessingUrl("/user/login") //form里面 action处理表单提交地址controller sping 提供
//                .defaultSuccessUrl("/user")
//                //The URL to send users if authentication fails.
//                .failureUrl("/failAuth")
//                .permitAll() //允许操作?
//                .and()
//                     //不需要登登陆认证的页面
//                    .authorizeRequests()
//                        .antMatchers("/index","/","/user/login").permitAll()
//                        //设置访问路径需要的权限
//                        .antMatchers("/user").hasAuthority("admin")
//                        .antMatchers("/auth").hasAuthority("auth")
//                        .antMatchers("/message").hasAnyAuthority("admin,auth")
//                        .antMatchers("/role").hasRole("role")
//                        .antMatchers("/roles").hasAnyRole("roles,otherRole")
//                .anyRequest().authenticated()//所有请求都可以访问
//                .and()
//                    //开启rememberMe 指定token数据库  由JdbcTokenRepositoryImpl 创建
//                    .rememberMe().tokenRepository(persistentTokenRepository())
//                    .tokenValiditySeconds(60)
//                    //通过myUserDetailsServiceImpl查询出user来和token表配对
//                    .userDetailsService(myUserDetailsServiceImpl);
//
//
//    }
//}
