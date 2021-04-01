package com.phoenixhell.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class TokenManager {
    //token 过期时间
    private final long tokenExpiration =24*60*60*1000;
    //编码密匙
    private final String tokenSignKey="123456";

    //根据用户名生产token
    public String createToken(String username){
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+ tokenExpiration))
                .signWith(SignatureAlgorithm.HS512,tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).compact();
        return  token;
    }

    //根据token字符串 获取用户信息
    public String getUserInfoFromToken(String token){
        String userInfo = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token).getBody().getSubject();
        return  userInfo;
    }

    //删除token
    public void removeToken(String token){
//        token=null;
    }
}
