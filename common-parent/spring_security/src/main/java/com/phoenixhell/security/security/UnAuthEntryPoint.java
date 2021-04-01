//package com.phoenixhell.security.security;
//
//import com.phoenixhell.utils.utils.R;
//import com.phoenixhell.utils.utils.ResponseUtil;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author phoenixhell
// * @create 2021/2/17 0017-下午 1:41
// */
//
//public class UnAuthEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        ResponseUtil.out(httpServletResponse, R.error());
//    }
//}
