package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.BlackListReq;

import java.util.List;

public interface BlackListService {
    BlackListReq save(BlackListReq blackListReq);
//    BlackListReq update(BlackListReq blackListReq);
    void delete(Long id);
//    List<BlackListReq> getBlackListByDiscountId(Long id);

}
