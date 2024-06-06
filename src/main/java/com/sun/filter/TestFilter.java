package com.sun.filter;

import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @date 2024/5/22
 */

@WebFilter(urlPatterns = {"/user/hello"},filterName = "testFilter")
public class TestFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我的过滤器");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
