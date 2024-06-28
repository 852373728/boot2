package com.sun.controller;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/hello")
    public Object hello(){
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication =
//                new TestingAuthenticationToken("username1", "password1", "ROLE_USER1");
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);


        SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        System.out.println(contextHolderStrategy);

        Map<String,Object> pa= new HashMap<>();
        pa.put("abc","哈哈");
        pa.put("url","/user/hello");
        return pa;
    }

}
