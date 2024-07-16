package com.sun.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvc implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowedOrigins("http://localhost:8866/").allowedMethods("*").allowedHeaders("*");
    }
}
