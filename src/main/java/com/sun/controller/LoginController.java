package com.sun.controller;

import com.sun.service.UserService;
import com.sun.vo.RegistrationUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2024/5/22
 */
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/")
    public Object home(){
        Map<String,Object> result = new HashMap<>();
        result.put("msg","登录成功");
        return  result;
    }

    @PostMapping("/registration")
    public Object registration(@RequestBody RegistrationUser registrationUser){

        userService.registration(registrationUser);

        Map<String,Object> obj= new HashMap<>();
        obj.put("msg","注册成功");
        return obj;
    }


}
