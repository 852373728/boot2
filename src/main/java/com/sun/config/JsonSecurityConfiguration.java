package com.sun.config;

import com.sun.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class JsonSecurityConfiguration {
    private static final Log logger = LogFactory.getLog(JsonSecurityConfiguration.class);

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http.authorizeRequests().
                antMatchers("/login","/error").permitAll().
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

        return http.build();
    }


}
