package com.sun;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @date 2024/5/23
 */
public class Chil {


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("123456");
        System.out.println(result);
    }
}
