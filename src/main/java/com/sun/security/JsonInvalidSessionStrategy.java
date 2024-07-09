package com.sun.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2024/7/9
 */
public class JsonInvalidSessionStrategy implements InvalidSessionStrategy {
    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (token!=null){
            response.setHeader(token.getHeaderName(),token.getToken());
        }
        logger.info("会话过期");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> result = new HashMap<>();
        result.put("message","登录已超时，请重新登录");
        result.put("status",false);

        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.OK.value());
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
