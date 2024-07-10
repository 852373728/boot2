package com.sun.controller;

import com.sun.event.UserEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public Object hello(HttpServletRequest request){
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        Authentication authentication =
//                new TestingAuthenticationToken("username1", "password1", "ROLE_USER1");
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        applicationContext.publishEvent(new UserEvent(principal));

        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("abc","孙麒麟");


        Map<String,Object> pa= new HashMap<>();
        pa.put("abc","哈哈");
        pa.put("url","/user/hello");
        return pa;
    }

}
