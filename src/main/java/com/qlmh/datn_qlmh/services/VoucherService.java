package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.dtos.request.AccountVoucherDto;
import com.qlmh.datn_qlmh.dtos.request.VoucherReq;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoucherService {
    VoucherReq save(VoucherReq voucherReq);
    VoucherReq update(VoucherReq voucherReq);
    void delete(Long id);
    List<AccountVoucherDto> get(String token);
    PageData<VoucherReq> findAll(Pageable pageable, String name, Integer status, String from, String to);
    VoucherReq findById(Long id);

}
