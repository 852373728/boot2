package com.sun.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @date 2024/6/28
 */
public class CaptchaFilter extends OncePerRequestFilter {

    public static final String SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private final AuthenticationFailureHandler failureHandler = new JsonAuthenticationFailureHandler();

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String captcha = request.getParameter(SECURITY_FORM_CAPTCHA_KEY);
        captcha = (captcha != null) ? captcha.trim() : "";

        HttpSession session = request.getSession(false);
        String sessionCaptcha = (String) session.getAttribute(SECURITY_FORM_CAPTCHA_KEY);
        session.removeAttribute(SECURITY_FORM_CAPTCHA_KEY);
        if (StringUtils.isNotBlank(sessionCaptcha) && sessionCaptcha.equalsIgnoreCase(captcha)){
            filterChain.doFilter(request,response);
        }else {
            this.failureHandler.onAuthenticationFailure(request,response,new CaptchaException("验证码输入有误，请重新输入！"));
        }

    }
}
