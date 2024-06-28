package com.sun.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Log logger = LogFactory.getLog(JsonAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (token!=null){
            response.setHeader(token.getHeaderName(),token.getToken());
        }
        logger.info("未登录返回json类型的消息");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> result = new HashMap<>();
        result.put("message","请先登录");
        result.put("status",false);

        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
