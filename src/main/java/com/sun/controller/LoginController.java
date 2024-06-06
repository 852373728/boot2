package com.sun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2024/5/22
 */
@RestController
public class LoginController {

    @GetMapping("/")
    public Object home(){
        Map<String,Object> result = new HashMap<>();
        result.put("msg","登录成功");
        return  result;
    }

}
