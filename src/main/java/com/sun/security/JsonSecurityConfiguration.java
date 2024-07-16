package com.sun.security;

import com.sun.service.MyPersistentTokenRepositoryService;
import com.sun.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;


@EnableWebSecurity
@Configuration
public class JsonSecurityConfiguration implements InitializingBean {
    private static final Log logger = LogFactory.getLog(JsonSecurityConfiguration.class);

    @Resource
    private MyPersistentTokenRepositoryService myPersistentTokenRepositoryService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, UserService userService) throws Exception {
        http.authorizeRequests().
                antMatchers("/login","/error","/captcha.jpg","/registration","/**.html","/anon/**").permitAll().
                antMatchers("/user/**").hasAnyAuthority("admin").
                antMatchers("/order/**").hasAnyAuthority("admin","user").
                antMatchers("/remember/**").rememberMe().
                anyRequest().authenticated();
        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = http.exceptionHandling();
        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new JsonAuthenticationEntryPoint());
        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new JsonAccessDeniedHandler());
        http.userDetailsService(userService);

        http.formLogin().
                failureHandler(new JsonAuthenticationFailureHandler()).
                successHandler(new JsonRequestAwareAuthenticationSuccessHandler());
        http.logout().logoutSuccessHandler(new JsonLogoutSuccessHandler()).addLogoutHandler(new JsonLogout());
        http.csrf().disable();

        http.sessionManagement().invalidSessionStrategy(new JsonInvalidSessionStrategy()).maximumSessions(1).expiredSessionStrategy(new JsonSessionInformationExpiredStrategy());
        //添加验证码过滤器
        // http.addFilterBefore(new CaptchaFilter(), UsernamePasswordAuthenticationFilter.class);

        RememberMeConfigurer<HttpSecurity> rememberMeConfigurer = http.rememberMe();
        String rememberKey = "sunqilin";
        rememberMeConfigurer.key(rememberKey);
        rememberMeConfigurer.rememberMeServices(new PersistentTokenBasedRememberMeServices(rememberKey,userService,myPersistentTokenRepositoryService));


        http.cors();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
