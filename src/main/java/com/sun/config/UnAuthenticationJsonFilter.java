package com.sun.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UnAuthenticationJsonFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(UnAuthenticationJsonFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication==null){

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,Object> result = new HashMap<>();
            result.put("message","请先登录");
            result.put("status",false);
            PrintWriter out = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            out.println(objectMapper.writeValueAsString(result));
            out.flush();
        }else {
            filterChain.doFilter(request,response);
        }

    }
}
