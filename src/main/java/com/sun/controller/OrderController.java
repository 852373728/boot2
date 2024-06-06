package com.sun.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @GetMapping("/getOne")
    public Object getOne() throws JsonProcessingException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Object principal = authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(authentication);
        Map<String,Object> pa= new HashMap<>();
        pa.put("gfd","小明");
        return s;
    }

    @GetMapping("/authhh")
    public Object authhh() throws JsonProcessingException {

        Map<String,Object> pa= new HashMap<>();
        pa.put("gfd","小明");
        return pa;
    }

}
