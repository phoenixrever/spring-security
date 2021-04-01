//package com.phoenixhell.security.security;
//
//import com.phoenixhell.utils.utils.MD5;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
////@Component
//public class DefaultPasswordEncoder implements PasswordEncoder {
//    @Override
//    public String encode(CharSequence charSequence) {
//        return MD5.encrypt(charSequence.toString());
//    }
//
//    @Override
//    public boolean matches(CharSequence charSequence, String encodedPassword) {
//        return encodedPassword.equals(MD5.encrypt(charSequence.toString()));
//    }
//}
