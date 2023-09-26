package com.qlmh.datn_qlmh.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        System.out.println(httpServletRequest.getRequestURL().toString());
        Enumeration<String> e = request.getAttributeNames();
        while (e.hasMoreElements()){
            String v = e.nextElement();
            System.out.println(" key : " + v + " --  value : " +request.getAttribute(v));
        }
        chain.doFilter(request, response);

    }
}
