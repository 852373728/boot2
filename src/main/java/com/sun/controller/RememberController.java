package com.sun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/remember")
public class RememberController {

    @GetMapping("/hello")
    public Object hello(HttpServletRequest request){

        Map<String,Object> pa= new HashMap<>();
        pa.put("abc","啦啦啦");
        pa.put("url","/remember/hello");
        return pa;
    }

}
