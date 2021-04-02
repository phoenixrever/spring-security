package com.phoenixhell.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author phoenixhell
 * @since 2021/4/2 0002-上午 11:09
 */

public class PasswordEncodeTest {
    @Test
    void encode() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
