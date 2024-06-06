package com.sun.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

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
        logger.info("未登录返回json类型的消息");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> result = new HashMap<>();
        result.put("message","请先登录");
        result.put("status",false);

        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.FORBIDDEN.value());
        out.println(objectMapper.writeValueAsString(result));
        out.flush();
    }
}
