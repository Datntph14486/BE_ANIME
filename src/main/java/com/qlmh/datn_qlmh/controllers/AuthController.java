package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.configs.mail.MailService;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.ChangePasswordDto;
import com.qlmh.datn_qlmh.dtos.request.ForgetPasswordto;
import com.qlmh.datn_qlmh.security.*;
import com.qlmh.datn_qlmh.services.AccountService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    AuthenticationSerive authenticationSerive;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtConstant jwtConstant;
    @PostMapping("/login")
    public Response login(@RequestBody LoginDto login, HttpServletResponse response){
        TokenDto tokenDto = authenticationSerive.authenticate(login);
//        Cookie c1 = new Cookie("access_token",tokenDto.getAccess_token());
////        c1.setHttpOnly(true);
//        c1.setMaxAge((int) (jwtConstant.getJwtExpiration()/1000));
//        c1.setPath("/api");
//        Cookie c2 = new Cookie("refresh_token",tokenDto.getRefresh_token());
////        c2.setHttpOnly(true);
//        c2.setPath("/api");
//        c2.setMaxAge((int) (jwtConstant.getRefresh().getJwtExpiration()/1000));
//        response.addCookie(c1);
//        response.addCookie(c2);
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,tokenDto);
    }
    @PostMapping("/logout")
    public Response logout(@RequestBody TokenDto tokenDto){
        System.out.println(tokenDto);
        authenticationSerive.logout(tokenDto);
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS);
    }
    @PostMapping("/logout-all")
    public Response logoutAll(@RequestBody TokenDto tokenDto){
        authenticationSerive.logoutAll(tokenDto);
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS);
    }
    @PostMapping("/refresh")
    public Response refresh(@RequestBody TokenDto tokenDto){
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,authenticationSerive.refresh(tokenDto));
    }
    @PostMapping("/register")
    public Response register(@RequestBody @Valid RegisterAccountDto registerAccountDto) throws MessagingException {
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,accountService.register(registerAccountDto));
    }
    @PostMapping("/register-bill")
    public Response registerAndBill(@RequestBody @Valid RegisterAccountDto registerAccountDto) {
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,accountService.registerAndBill(registerAccountDto));
    }
    @GetMapping("/register/{token}")
    public Response registerConfirm(@PathVariable(name = "token") String token) throws MessagingException {
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,accountService.registerConfirm(token));
    }
    @PostMapping("/change-password")
    public Response changePassword(@Valid @RequestBody ChangePasswordDto passwordDto) {
        accountService.changePassword(passwordDto);
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS);
    }
    @GetMapping("/forget-password")
    public Response forgetPassword(@Valid @RequestParam("email") String email) throws MessagingException {
        accountService.forgetPassword(email);
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS);
    }
    @PostMapping("/confirm-password")
    public Response confirm(@Valid @RequestBody ForgetPasswordto forgetPasswordto) throws MessagingException {
        accountService.confirm(forgetPasswordto);
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS);
    }

    @PostMapping("/confirm-isadmin")
    public Boolean getRole(@Valid @RequestBody TokenDto tokenDto) throws MessagingException {
        if(tokenDto.getAccess_token() == null){
            return false;
        }
        return accountService.isAdmin(tokenDto.getAccess_token());
    }

    @GetMapping("/get-role")
    public Response getRole(HttpServletRequest request){
        String access_token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);;
        if(access_token== null || access_token.equals("")){
            return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,new ArrayList<>());
        }
        return new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,accountService.getRole(access_token));
    }
}
