
package com.qlmh.datn_qlmh.services;


import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.dtos.request.ChangePasswordDto;
import com.qlmh.datn_qlmh.dtos.request.ForgetPasswordto;
import com.qlmh.datn_qlmh.dtos.response.AccountRes;
import com.qlmh.datn_qlmh.entities.AccountEntity;
import com.qlmh.datn_qlmh.entities.AccountRoleEntity;
import com.qlmh.datn_qlmh.entities.RoleEntity;
import com.qlmh.datn_qlmh.security.RegisterAccountDto;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface AccountService {

    public AccountEntity create(AccountEntity account);
    public AccountEntity update(AccountEntity account);
    public List<AccountRes> getAll();
    public PageData<AccountRes> getAll(Pageable pageable);
    public List<AccountRoleEntity > getAllAccountRole();
    public List<RoleEntity> getAllRoleEntity();
public  AccountRoleEntity addAuthorize(AccountRoleEntity accountRole);
public AccountRoleEntity deleteAuthorize(Long id);
List <AccountRoleEntity> getAccountRoleById(String id);
    public AccountEntity getByUsername(String username);
    public List<AccountEntity> listAdmin();
    public boolean isAdmin(String token);

    public RegisterAccountDto register(RegisterAccountDto registerAccountDto) throws MessagingException;
    RegisterAccountDto registerAndBill(RegisterAccountDto registerAccountDto);
    public RegisterAccountDto registerConfirm(String token);
    public void changePassword(ChangePasswordDto changePasswordDto);
    public void forgetPassword(String email) throws MessagingException;
    public void confirm(ForgetPasswordto forgetPasswordto) throws MessagingException;
     public List<String> getRole(String request);

}


