package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.VoucherAccountReq;
import com.qlmh.datn_qlmh.services.VoucherAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/api/voucher-account")
@CrossOrigin("*")
public class VoucherAccountController {
    @Autowired
    private VoucherAccountService voucherAccountService;
    @GetMapping("/{id}")
    public ResponseEntity<Response> getVoucherProductById(@PathVariable("id") Long id,
                                                          @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size",required = false,defaultValue = "5" )Integer pageSize,
                                                          @RequestParam(name = "username",required = false,defaultValue = "" )String username,
                                                          @RequestParam(name = "fullname",required = false,defaultValue = "" )String fullname
                                                          ){
        Pageable pageable = PageRequest.of(page,pageSize);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),
                ResponseTemplate.SUCCESS,voucherAccountService.getAllById(id,pageable,username,fullname)
                ));
    }
    @PostMapping("/save")
    public ResponseEntity<Response> save(@Valid @RequestBody VoucherAccountReq voucherAccountReq){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),
                ResponseTemplate.SUCCESS,
                voucherAccountService.add(voucherAccountReq)
        ));
    }
    @PostMapping("/disable")
    public ResponseEntity<Response> disable(@Valid @RequestBody VoucherAccountReq voucherAccountReq){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),
                ResponseTemplate.SUCCESS,voucherAccountService.disable(voucherAccountReq)
        ));
    }
    @GetMapping("/user")
    public ResponseEntity<Response> getUsername(@RequestParam("username") String username){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),
                ResponseTemplate.SUCCESS,voucherAccountService.accountEntity(username)
        ));
    }
    @GetMapping("/voucher-user")
    public ResponseEntity<Response> getVoucherByToken(@RequestParam("token") String username){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),
                ResponseTemplate.SUCCESS,voucherAccountService.accountEntity(username)
        ));
    }
}
