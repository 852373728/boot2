package com.sun.config;

import com.sun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.util.StringUtils;

import java.util.List;


@EnableWebSecurity
@Configuration
@Slf4j
public class MySecurityConfiguration{


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http.authorizeRequests().
                antMatchers("/login","/error").anonymous().
                anyRequest().authenticated();

        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = http.exceptionHandling();
        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new JsonAuthenticationEntryPoint());

        http.userDetailsService(userService);

        http.formLogin().failureHandler(new MyAuthenticationFailureHandler());

        // http.addFilterAfter(new UnAuthenticationJsonFilter(), SecurityContextPersistenceFilter.class);
        return http.build();
    }


}
