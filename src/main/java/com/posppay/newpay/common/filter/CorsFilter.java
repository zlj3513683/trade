package com.posppay.newpay.common.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "CorsFilter")
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-control-allow-origin", "*");
        response.setHeader("Access-control-allow-headers", "x-requested-with,content-type");
        response.setHeader("Access-control-allow-methods", "POST");
        chain.doFilter(req, res);
    }


    @Override
    public void init(FilterConfig filterConfig) {
//        System.out.println("=======================================================chulaile=================================================");
    }

    @Override
    public void destroy() {}

}
