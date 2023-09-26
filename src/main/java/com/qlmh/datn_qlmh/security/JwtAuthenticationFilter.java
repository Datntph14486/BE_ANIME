package com.qlmh.datn_qlmh.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AccountDetailService accountDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        String username = null;
        System.out.println(request.getServletPath().contains("/api/auth/"));
        if(isPermitted(request)){
            filterChain.doFilter(request, response); // chạy filter bên dưới
            return; // ngưng không chay code bên dưới
        }
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response); // chay filter bên dưới
//            return; // ngưng ko chạy code bên dưới
//        }
        if(authHeader == null){
            response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Access denied")));
            return;
        }
        jwt = authHeader.substring(7);// "Bearer " co 7 ki tu
        try {
            username = jwtService.getUsernameFromJwtToken(jwt,true);
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(new ObjectMapper().writeValueAsString(new ErrorResponse(HttpStatus.FORBIDDEN.value(), "JWT is invalid or has experied")));
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = accountDetailService.loadUserByUsername(username);
            if (jwtService.isValidToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null,
                        userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean isPermitted(HttpServletRequest request) {
        return request.getServletPath().equals("/user/login")
                || request.getServletPath().contains("/api/auth/")
                || request.getServletPath().contains("/api/v1/cart")
                || request.getServletPath().equals("/api/test-vnpay")
                || request.getServletPath().equals("/api/test-vnpay-return")
                || request.getServletPath().contains("/api/v1/bill/export")
                || request.getServletPath().equals("/api/rate/show")
                || request.getServletPath().contains("/api/products")
                || request.getServletPath().equals("/api/product-rate/start")
                || request.getServletPath().equals("/api/rate/get-by-productid")
                || request.getServletPath().contains("/v3/api-docs")
                || request.getServletPath().contains("/swagger-ui")
                || request.getServletPath().contains("api/categories")
                || request.getServletPath().contains("/api/success-payment")
                || request.getServletPath().contains("/api/vnpay/")
                || request.getServletPath().contains("/api/create-pay")
                || request.getServletPath().contains("/api/manufacturer")
                || request.getServletPath().contains("/api/v1/bill/discount-bill")
                || request.getServletPath().contains("/api/v1/bill/find-address")
                || request.getServletPath().contains("/api/v1/bill/applyVoucher")
                || request.getServletPath().contains("/api/v1/bill/save")
                || request.getServletPath().contains("/api/v1/bill/username")
                || request.getServletPath().contains("/api/v1/bill/xac_nhan")
                || request.getServletPath().contains("/api/v1/bill/update-refund")
                || request.getServletPath().contains("/api/v1/bill")
                || request.getServletPath().contains("/api/find-pay")


                ;
    }
}
