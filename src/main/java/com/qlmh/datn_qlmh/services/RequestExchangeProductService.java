package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.RequestExchangeProductReq;

import java.util.List;

public interface RequestExchangeProductService {
    RequestExchangeProductReq create(RequestExchangeProductReq requestExchangeProductReq);
    RequestExchangeProductReq update(RequestExchangeProductReq requestExchangeProductReq);

    List<RequestExchangeProductReq> getAll();


}
