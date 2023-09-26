//package com.qlmh.datn_qlmh.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
////@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider{
//    @Autowired
//    private AccountDetailService accountDetailService;
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // database resource
//        System.err.println("Custom Authentication Provider");
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        UserDetails accountDetail = accountDetailService.loadUserByUsername(username);
//        return new UsernamePasswordAuthenticationToken(username, password, accountDetail.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        // TODO Auto-generated method stub
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}