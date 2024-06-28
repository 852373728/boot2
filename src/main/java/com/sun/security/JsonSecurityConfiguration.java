package com.sun.security;

import com.sun.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
public class JsonSecurityConfiguration implements InitializingBean {
    private static final Log logger = LogFactory.getLog(JsonSecurityConfiguration.class);

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http.authorizeRequests().
                antMatchers("/login","/error","/captcha.jpg").permitAll().
                antMatchers("/user/**").hasAnyAuthority("admin").
                antMatchers("/order/**").hasAnyAuthority("admin","user").
                anyRequest().authenticated();
        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = http.exceptionHandling();
        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new JsonAuthenticationEntryPoint());
        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new JsonAccessDeniedHandler());
        http.userDetailsService(userService);

        http.formLogin().
                failureHandler(new JsonAuthenticationFailureHandler()).
                successHandler(new JsonRequestAwareAuthenticationSuccessHandler());
        http.logout().logoutSuccessHandler(new JsonLogoutSuccessHandler());
        http.csrf().disable();

        http.addFilterBefore(new CaptchaFilter(), UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
