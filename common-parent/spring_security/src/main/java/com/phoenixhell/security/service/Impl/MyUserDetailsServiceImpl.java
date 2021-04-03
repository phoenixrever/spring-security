package com.phoenixhell.security.service.Impl;

import com.phoenixhell.security.entity.User;
import com.phoenixhell.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/4/2 0002-上午 9:34
 * 实现UserDetailsService 操作数据库(伪)
 */
//@Service
//@Primary
//@Service("userDetailsService")
public class MyUserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //传入字符串形成collection  实际中 数据库中直接查 生成collection
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("admin,customer");
//        return new User("shadow", new BCryptPasswordEncoder().encode("1234"),grantedAuthorities);
        //=================================================================================
        //实际查询数据库(根据username查询出对应user生成spring user对象
        // user对象的密码和登陆的密码比较 相同登陆成功)
        User user = userService.query().eq("nickname", username).one();
        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_customer,admin,postfilter");
        return  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),grantedAuthorities);
    }
}
