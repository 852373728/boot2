package com.sun.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    Log logger = LogFactory.getLog(getClass());

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        logger.info("已在别处登录");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> result = new HashMap<>();
        result.put("message","该账号已在别处登录，当前登录已失效");
        result.put("status",false);

        PrintWriter out = response.getWriter();

        response.setStatus(HttpStatus.OK.value());
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();

    }
}
