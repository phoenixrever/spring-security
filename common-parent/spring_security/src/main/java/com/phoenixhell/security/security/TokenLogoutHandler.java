package com.phoenixhell.security.security;

import com.phoenixhell.utils.utils.R;
import com.phoenixhell.utils.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author phoenixhell
 * @create 2021/2/17 0017-下午 1:17
 * 退出logout处理器
 */
@Component
public class TokenLogoutHandler implements LogoutHandler {
    @Resource
    private TokenManager tokenManager;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader("token");
        if(token!=null){
            //从token里面获取用户名
            String username = tokenManager.getUserInfoFromToken(token);
            //移除
            tokenManager.removeToken(token);
            redisTemplate.delete(username);
        }
       ResponseUtil.out(httpServletResponse, R.ok());
    }
}
