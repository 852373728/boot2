package com.sun.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.annotation.Resource;

@Configuration
@EnableRedisHttpSession
public class JsonRedisHttpSessionConfiguration {

    private static final Log logger = LogFactory.getLog(JsonRedisHttpSessionConfiguration.class);


    @Resource
    private RedisIndexedSessionRepository sessionRepository;

    @Bean
    public SpringSessionBackedSessionRegistry<?> sessionRegistry(){
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }



}
