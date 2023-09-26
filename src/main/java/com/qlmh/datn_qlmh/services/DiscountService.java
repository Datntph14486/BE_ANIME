package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import org.springframework.data.domain.Pageable;

public interface DiscountService {
    DiscountReq save(DiscountReq voucherReq);
    DiscountReq update(DiscountReq voucherReq);
    void delete(Long id);
    PageData<DiscountReq> findAll(Pageable pageable, String name, Integer status, String from, String to);
    DiscountReq findById(Long id);

}
