package com.phoenixhell.security.service.Impl;

import com.phoenixhell.security.entity.SecurityUser;
import com.phoenixhell.security.entity.User;
import com.phoenixhell.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author phoenixhell
 * @since 2021/4/3 0003-下午 4:45
 */
@Service("userDetailsService")
public class TokenUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.query().eq("nickname", username).one();
        SecurityUser securityUser = new SecurityUser(user);
        return securityUser;
    }
}
