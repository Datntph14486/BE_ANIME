
package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.configs.RegisterForThymeleaf;
import com.qlmh.datn_qlmh.configs.mail.MailService;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.response.AccountRes;
import com.qlmh.datn_qlmh.entities.AccountEntity;
import com.qlmh.datn_qlmh.entities.AccountRoleEntity;
import com.qlmh.datn_qlmh.entities.RoleEntity;

import com.qlmh.datn_qlmh.configs.FontEndConfig;
import com.qlmh.datn_qlmh.dtos.request.ChangePasswordDto;
import com.qlmh.datn_qlmh.dtos.request.ForgetPasswordto;
import com.qlmh.datn_qlmh.exceptions.EntityAlreadyExistsException;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.AccountRepo;
import com.qlmh.datn_qlmh.security.AccountRoleRepo;
import com.qlmh.datn_qlmh.security.JwtService;
import com.qlmh.datn_qlmh.security.RegisterAccountDto;

import com.qlmh.datn_qlmh.security.RoleRepo;
import com.qlmh.datn_qlmh.services.AccountService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final int expiredMinute=15;
    @Autowired
    MailService mailService;
    @Autowired
    Mapper mapper;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    AccountRoleRepo accountRoleRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    FontEndConfig fontEndConfig;
    @Autowired
    JwtService jwtService;
    @Override
    public AccountEntity create(AccountEntity account) {
        if (account.getPhone() == null) {
            throw new NotFoundException("Phone is not null ");
        }
        if (account.getFullname() == null) {
            throw new NotFoundException("FullName is not null ");
        }
        if (account.getUserName() == null || account.getUserName().equals("")) {
            throw new NotFoundException("UserName is not null ");
        }
        if (account.getPassword() == null) {
            throw new NotFoundException("Password is not null ");
        }
        if (account.getEmail() == null) {
            throw new NotFoundException("Email is not null ");
        }
        Optional<AccountEntity> accountEntity = accountRepo.findByUsername(account.getUserName());
        if (accountEntity.isEmpty()) {
            account.setPassword(encoder.encode(account.getPassword()));
            AccountEntity account1 = accountRepo.save(account);
            AccountRoleEntity accountRoleEntity = new AccountRoleEntity();
            accountRoleEntity.setRoleId(2l);
            accountRoleEntity.setUserName(account1.getUserName());
            accountRoleRepo.save(accountRoleEntity);
            return account1;
        } else {
            throw new NotFoundException("account da ton tai ");
        }
    }

    @Override
    public AccountEntity update(AccountEntity account) {
        return null;
    }

    @Override
    public List<AccountRes> getAll() {
        List<AccountEntity>  accounts =  accountRepo.findAll();
        List<AccountRes> accountRess = new ArrayList<>();
        for (AccountEntity a: accounts){
            AccountRes accountRes = new AccountRes();
            accountRes.setBirthday(a.getBirthday());
            accountRes.setFullname(a.getFullname());
            accountRes.setPhone(a.getPhone());
            accountRes.setEmail(a.getEmail());
            accountRes.setUrlImg("https://www.invert.vn/media/uploads/uploads/2022/12/06172901-11.jpeg");
            accountRes.setStatus(a.getStatus());
            List<AccountRoleEntity>  accountRoleEntity = accountRoleRepo.getByUsername(a.getUserName());
            accountRes.setRole(accountRoleEntity.get(0).getRoleId());
            accountRes.setUserName(a.getUserName());
            accountRess.add(accountRes);
        }

        return accountRess;
    }

    @Override
    public PageData<AccountRes> getAll(Pageable pageable) {
        Page<AccountEntity> accounts =  accountRepo.findAll(pageable);
        List<AccountRes> accountRess = new ArrayList<>();
        for (AccountEntity a: accounts){
            AccountRes accountRes = new AccountRes();
            accountRes.setBirthday(a.getBirthday());
            accountRes.setFullname(a.getFullname());
            accountRes.setPhone(a.getPhone());
            accountRes.setEmail(a.getEmail());
            accountRes.setUrlImg("https://www.invert.vn/media/uploads/uploads/2022/12/06172901-11.jpeg");
            accountRes.setStatus(a.getStatus());
            List<AccountRoleEntity>  accountRoleEntity = accountRoleRepo.getByUsername(a.getUserName());
//            accountRes.setRole(accountRoleEntity.get(0).getRoleId());
            accountRes.setUserName(a.getUserName());
            accountRes.setAccountRoleEntities(accountRoleEntity);
            accountRess.add(accountRes);
        }
        return PageData.of(accounts,accountRess);
    }

    @Override
    public List<AccountRoleEntity> getAllAccountRole() {
        return accountRoleRepo.findAll();
    }

    @Override
    public List<RoleEntity> getAllRoleEntity() {
        return roleRepo.findAll();
    }

    @Override
    public AccountRoleEntity addAuthorize(AccountRoleEntity accountRole) {
        Optional<AccountRoleEntity> optional =accountRoleRepo.getByRoleAndUsername(accountRole.getRoleId(), accountRole.getUserName());
        if(optional.isPresent()){
            System.out.println("present");
            return optional.get();
        }
        else{
            System.out.println("present");

        return accountRoleRepo.save(accountRole);
        }
    }

    @Override
    public AccountRoleEntity deleteAuthorize(Long id) {
        Optional<AccountRoleEntity> accountRole = accountRoleRepo.findById(id);
        accountRoleRepo.delete(accountRole.get());
        return null;
    }

    @Override
    public List <AccountRoleEntity> getAccountRoleById(String id) {
        return accountRoleRepo.getByUsername(id);
    }


//    @Override
//    public AccountEntity updateStatus(String username, Integer status) {
//       AccountEntity account = accountRepo.
//        return null;
//    }

    @Override
    public AccountEntity getByUsername(String username) {
        Optional<AccountEntity> account = accountRepo.findByUsername(username);
        if (account.isPresent()) {
            return account.get();
        } else {
            throw new NotFoundException("Account invalid");
        }
    }

    @Override
    public List<AccountEntity> listAdmin() {
        return null;

//        return accountRepo.fiByRole(Role.ADMIN);
//

    }

    @Override
    public boolean isAdmin(String token) {
        Claims claims = jwtService.extractAllClaims(token,true);
        ArrayList scopes = (ArrayList) claims.get("ROLE");
        boolean tmp = false;
        for (int i = 0; i < scopes.size() ; i++) {
            if(scopes.get(i).equals(Constant.AccountRole.ADMIN)){
                tmp = true;
                break;
            }
        }
        return tmp;
    }

    @Override
    public RegisterAccountDto register(RegisterAccountDto registerAccountDto) throws MessagingException {
        Optional<AccountEntity> optional = accountRepo.findByUsernameOrEmail(registerAccountDto.getUserName(), registerAccountDto.getEmail());
        if(optional.isPresent()){
            if(optional.get().getStatus() == Constant.AccountStatus.NON_ACTIVE){
                accountRepo.deleteById(optional.get().getUserName());
            }else{
                throw new EntityAlreadyExistsException("Username or email is already used");
            }
        }

        AccountEntity accountEntity = mapper.toAccountEntity(registerAccountDto);
        accountEntity.setPassword(encoder.encode(registerAccountDto.getPassword()));
        accountEntity.setToken(System.currentTimeMillis()+"_"+ UUID.randomUUID().toString());
        accountEntity.setExpiredToken(new Date(System.currentTimeMillis()+expiredMinute*60*1000));
        accountEntity.setStatus(Constant.AccountStatus.NON_ACTIVE);
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromHttpUrl(registerAccountDto.getUrl());
//        builder.scheme("https");
//        builder.pathSegment(accountEntity.getToken());
        builder.queryParam("token",accountEntity.getToken());
        URI newUri = builder.build().toUri();

        String body ="<p>Xin chào bạn .Bạn đã đăng kí tài khoản của website Japan . Vui lòng "
                + "<a href=\""+newUri.toString()+"\">XÁC THỰC</a> "
                + "tài khoản qua đường dẫn</p>. Vui lòng xác thực email này trước "+new SimpleDateFormat(Constant.DATE_FORMAT).format(accountEntity.getExpiredToken());
        Map<String,Object> map = new HashMap<>();
        System.out.println(newUri.toString());
        map.put("name",accountEntity.getFullname());
        map.put("url",newUri.toString());
        mailService.queue(accountEntity.getEmail(), "Xác thực tài khoản", body,new RegisterForThymeleaf(map));
        accountRepo.save(accountEntity);
        return null;
    }

    @Override
    public RegisterAccountDto registerAndBill(RegisterAccountDto registerAccountDto) {
        RegisterAccountDto response = new RegisterAccountDto();
        AccountEntity accountEntity = new AccountEntity();
        AccountRoleEntity accountRole = new AccountRoleEntity();
        Optional<AccountEntity> optional = accountRepo.findByEmail( registerAccountDto.getEmail());
        if(optional.isPresent()){
            accountEntity = accountRepo.save(optional.get());
        }else{
            accountEntity = mapper.toAccountEntity(registerAccountDto);
            accountEntity.setPassword(encoder.encode(registerAccountDto.getPassword()));
            accountEntity.setToken(System.currentTimeMillis()+"_"+ UUID.randomUUID().toString());
            accountEntity.setExpiredToken(new Date(System.currentTimeMillis()+expiredMinute*60*1000));
            accountEntity.setStatus(Constant.AccountStatus.ACTIVE);
            accountRepo.save(accountEntity);

            accountRole.setRoleId(2l);
            accountRole.setUserName(accountEntity.getUserName());
            accountRoleRepo.save(accountRole);
        }

        response.setEmail(accountEntity.getEmail());
        response.setUserName(accountEntity.getUserName());
        return response;
    }

    @Override
    public RegisterAccountDto registerConfirm(String token) {
        AccountEntity accountEntity =  accountRepo.findByToken(token).orElseThrow(()->new InvalidArgumentException("Token is invalid"));
        if(new Date().after(accountEntity.getExpiredToken()) || accountEntity.getStatus() != Constant.AccountStatus.NON_ACTIVE){
            throw new InvalidArgumentException(("Token is expired. Try again"));
        }
        else{
            accountEntity.setToken("");
            accountEntity.setExpiredToken(null);
            accountEntity.setStatus(1);
            accountRepo.save(accountEntity);

            accountRoleRepo.save(new AccountRoleEntity(accountEntity.getUserName(),Constant.AccountRole.CLIENT_ID));

        }

        return null;
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        if(changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword()) == false){
            throw new InvalidArgumentException("new password not match");
        }
        AccountEntity accountEntity = accountRepo.findByUsername(changePasswordDto.getUsername()).orElseThrow(()-> new NotFoundException("Username or password not exactly"));
        if(encoder.matches(changePasswordDto.getOldPassword(),accountEntity.getPassword())== false){
            throw new InvalidArgumentException("Username or password not exactly");
        }
        accountEntity.setPassword(encoder.encode(changePasswordDto.getNewPassword()));
        accountRepo.save(accountEntity);
    }

    @Override
    public void forgetPassword(String email) throws MessagingException {
        AccountEntity accountEntity = accountRepo.findByUsernameOrEmail("",email).orElseThrow(()-> new NotFoundException("Email not found"));
        if(accountEntity.getStatus() == Constant.AccountStatus.NON_ACTIVE){
            throw new InvalidArgumentException("Account not exists");
        }
        accountEntity.setToken(System.currentTimeMillis()+"_"+ UUID.randomUUID().toString());
        accountEntity.setExpiredToken(new Date(System.currentTimeMillis()+expiredMinute*60*1000));
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromHttpUrl(fontEndConfig.getUrl());
//        builder.scheme("https");
//        builder.pathSegment(accountEntity.getToken());
        builder.pathSegment("forget-password","confirm");
        builder.queryParam("token",accountEntity.getToken());
        URI newUri = builder.build().toUri();
        System.out.println(newUri.toString());
        Map<String,Object> map = new HashMap<>();
        map.put("name",accountEntity.getUserName());
        map.put("url",newUri.toString());
        RegisterForThymeleaf forgetPassword = new RegisterForThymeleaf(map);
        forgetPassword.setPath("forget_password");
        mailService.queue(accountEntity.getEmail(), "Quên mật khẩu", "body",forgetPassword);
        accountRepo.save(accountEntity);
    }

    @Override
    public void confirm(ForgetPasswordto forgetPasswordto) throws MessagingException {
        if(forgetPasswordto.getNewPassword().equals(forgetPasswordto.getConfirm())== false){
            throw new InvalidArgumentException("new password not match");
        }
        AccountEntity accountEntity = accountRepo.findByToken(forgetPasswordto.getToken()).orElseThrow(()->new InvalidArgumentException("Token is invalid"));
        if(new Date().after(accountEntity.getExpiredToken()) || accountEntity.getStatus() == Constant.AccountStatus.NON_ACTIVE){
            throw new InvalidArgumentException(("Token is expired. Try again"));
        }
        accountEntity.setPassword(encoder.encode(forgetPasswordto.getNewPassword()));
        accountEntity.setExpiredToken(null);
        accountEntity.setToken(null);
        accountRepo.save(accountEntity);
    }

    @Override
    public List<String> getRole(String request) {
        String us = jwtService.getUsernameFromJwtToken(request,true);
        return accountRoleRepo.getAccountRoleEntityByUserName(us).stream().map(i->i.getRoleEntity().getRoleName()).collect(Collectors.toList());
    }
}
