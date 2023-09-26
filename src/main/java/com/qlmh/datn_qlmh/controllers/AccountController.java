
package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.response.AccountRes;
import com.qlmh.datn_qlmh.entities.AccountEntity;
import com.qlmh.datn_qlmh.entities.AccountRoleEntity;
import com.qlmh.datn_qlmh.entities.RoleEntity;
import com.qlmh.datn_qlmh.services.AccountService;
import com.qlmh.datn_qlmh.services.UploadfileService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    UploadfileService uploadfileService;

    @RolesAllowed({Constant.AccountRole.ADMIN})

    @GetMapping("/getAll")
    public PageData<AccountRes> getAll(
            @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
            @RequestParam(name = "size",required = false,defaultValue = "5" )Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(page,pageSize);
        PageData<AccountRes> accountRes = accountService.getAll(pageable);
        return accountRes;
    }
//    @GetMapping("/getAll")
//    public ResponseEntity<Response> getAll(
//            @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
//            @RequestParam(name = "size",required = false,defaultValue = "5" )Integer pageSize,
//            @RequestParam(name = "search",required = false,defaultValue = "") String search,
//            @RequestParam(name = "status",required = false) Integer status,
//            @RequestParam(name = "from",required = false) String from,
//            @RequestParam(name = "to",required = false) String to
//    ) {
//        System.out.println(from + " - " + to);
//        Sort sort = Sort.by(Sort.Direction.DESC,"discountStart");
//        Pageable pageable = PageRequest.of(page, pageSize,sort);
//        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,accountService.getAll( pageable,  search,  status,  from,  to)));
//
//    }
    @PostMapping("/new")
    public ResponseEntity<AccountEntity> addNewAccount(@RequestBody AccountEntity account){

        AccountEntity saveAccount = accountService.create(account);
        return ResponseEntity.ok(saveAccount);
    }
    @PutMapping("/update")
    public ResponseEntity<AccountEntity> updateAccount(@RequestBody AccountEntity account){
        AccountEntity updateAccount = accountService.update(account);
        return ResponseEntity.ok(updateAccount);
    }
//    @PutMapping("/updateStatus")
//    public ResponseEntity<AccountEntity> updateStatusAccount(@RequestParam String username, @RequestParam Integer status){
////        AccountEntity updateAccountStatus = accountService.update(username,status);
//        return ResponseEntity.ok(updateAccountStatus);
//    }
//@PostMapping("/upload")
//public  String[] upload(@RequestParam("files") MultipartFile[] files) {
//    if(files.length >0){
//        String[] a =  uploadfileService.upload(files);
//        return a;
//    }else{
//        return null;
//    }
//
//}
//    @GetMapping("/show")
//    public ResponseEntity<byte[]> show(@RequestParam("url") String url) throws IOException {
//
//        return ResponseEntity.ok(uploadfileService.show(url));
//    }
@RolesAllowed({Constant.AccountRole.ADMIN})
    @GetMapping("/role")
    public List<RoleEntity> getAllRoleEntity() {
        return accountService.getAllRoleEntity();
    }
    @RolesAllowed({Constant.AccountRole.ADMIN})
    @GetMapping("/account-role")
    public List<AccountRoleEntity> getAllAccountRoleEntities() {
        return accountService.getAllAccountRole();
    }

    @RolesAllowed({Constant.AccountRole.ADMIN})
    @PostMapping("/accountRole")
    public  AccountRoleEntity addAuthorize( @RequestBody AccountRoleEntity accountRole) {
        return accountService.addAuthorize(accountRole);
    }
    @RolesAllowed({Constant.AccountRole.ADMIN})
    @DeleteMapping("/accountRole")
    public AccountRoleEntity deleteAuthorize(@RequestParam("id") Long id) {
        return accountService.deleteAuthorize(id);
    }
    @RolesAllowed({Constant.AccountRole.ADMIN})
    @GetMapping("/accountRole/{id}")
    public List <AccountRoleEntity> getAuthorize(@PathVariable("id") String id) {
        return accountService.getAccountRoleById(id);
    }
}

