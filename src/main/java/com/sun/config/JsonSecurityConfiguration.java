package com.sun.config;

import com.sun.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class JsonSecurityConfiguration {
    private static final Log logger = LogFactory.getLog(JsonSecurityConfiguration.class);

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http.authorizeRequests().
                antMatchers("/login","/error").anonymous().
                anyRequest().authenticated();

        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = http.exceptionHandling();
        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new JsonAuthenticationEntryPoint());
        http.userDetailsService(userService);

        http.formLogin().failureHandler(new JsonAuthenticationFailureHandler());

        return http.build();
    }


}
