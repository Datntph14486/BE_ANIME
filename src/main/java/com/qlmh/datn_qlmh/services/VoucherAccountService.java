package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.VoucherAccountDto;
import com.qlmh.datn_qlmh.dtos.request.VoucherAccountReq;
import com.qlmh.datn_qlmh.entities.AccountEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherAccountService {
    public VoucherAccountReq add(VoucherAccountReq voucherAccountReq);
    public VoucherAccountReq getByToken(String token);
    public List<AccountEntity> findAll();
    public VoucherAccountDto getAllById(Long id, Pageable pageable,String username,String fullname);
    public VoucherAccountReq disable(VoucherAccountReq id);
    public void useVoucher(VoucherAccountReq voucherAccountReq);
    public AccountEntity accountEntity(String s);

}
