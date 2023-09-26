package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.DiscountPlusReq;

public interface DiscountPlusService {
    public DiscountPlusReq save(DiscountPlusReq discountPlusReq);
    public DiscountPlusReq update(DiscountPlusReq discountPlusReq);
    public DiscountPlusReq findByDiscountProductId(Long id);
}
