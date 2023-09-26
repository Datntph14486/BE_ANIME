package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.BlackListReq;
import com.qlmh.datn_qlmh.dtos.request.ProductDiscountReq;

import java.util.List;

public interface ProductDiscountService {
    ProductDiscountReq save(ProductDiscountReq blackListReq);
    //    BlackListReq update(BlackListReq blackListReq);
    void delete(Long id);
    List<ProductDiscountReq> getBlackListByDiscountId(Long id);
}
