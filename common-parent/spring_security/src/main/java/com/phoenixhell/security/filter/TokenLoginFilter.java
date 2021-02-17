package com.phoenixhell.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixhell.security.entity.SecurityUser;
import com.phoenixhell.security.entity.User;
import com.phoenixhell.security.security.TokenManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author phoenixhell
 * @create 2021/2/17 0017-下午 1:47
 */
@Component
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Resource
    private TokenManager tokenManager;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AuthenticationManager authenticationManager;

    public TokenLoginFilter() {
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("admin/acl/login", "POST"));
    }

    //获取表单提交的用户名密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //jackson json java 对象转换  从user json字符窜反序列化生产user对象
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("转换user失败");
        }
    }

    //认证成功调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //认证成功,得到认证成功之后的用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        //根据用户名生产token
        super.successfulAuthentication(request, response, chain, authResult);
    }

    //认证失败调用
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
