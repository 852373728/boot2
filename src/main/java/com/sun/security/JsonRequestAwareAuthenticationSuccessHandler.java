package com.sun.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2024/6/20
 */
@Slf4j
public class JsonRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        clearAuthenticationAttributes(request);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        log.info("登录成功：{}",authentication);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> result = new HashMap<>();
        result.put("message","登录成功");
        result.put("status",true);
        result.put("object",authentication);

        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.OK.value());
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();

    }
}
