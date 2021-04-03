package com.phoenixhell.security.filter;

import com.phoenixhell.security.security.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenAuthFilter extends BasicAuthenticationFilter {
    @Resource
    private TokenManager tokenManager;
    @Resource
    private RedisTemplate redisTemplate;


    /*
    java默认的在调用子类构造方法前先调用父类的构造方法，
    如果你没有指定调用父类的哪个构造方法，
    那么java默认调用父类无参数的构造方法
    BasicAuthenticationFilter没有无参
    所以必须实现父类带参数构造方法
    */
    public TokenAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if(authentication!=null){
            //权限上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        //header里面获取token
        String token = request.getHeader("token");
        if(token!=null){
            //从token里面获取用户名
            String username = tokenManager.getUserInfoFromToken(token);
            //从redis 获取对应权限列表
             List<String> permissions= (List<String>) redisTemplate.opsForValue().get(username);
             //Collection<? extends GrantedAuthority> authorities
            Collection<GrantedAuthority> authorities =new ArrayList<>();
            permissions.forEach((p)->authorities.add(new SimpleGrantedAuthority(p)));
             return new UsernamePasswordAuthenticationToken(username, token, authorities);
        }
        return null;
    }
}
