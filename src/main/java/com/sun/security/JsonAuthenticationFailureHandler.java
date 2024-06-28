package com.sun.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Log logger = LogFactory.getLog(JsonAuthenticationFailureHandler.class);


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        logger.info(exception.getMessage());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> result = new HashMap<>();
        result.put("message",exception.getMessage());
        result.put("status",false);

        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
