package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ExchangeProductReq;

import java.util.List;

public interface ExchangeProductService {
    ExchangeProductReq create(ExchangeProductReq exchangeProductReq);
    ExchangeProductReq update(ExchangeProductReq exchangeProductReq);

    List<ExchangeProductReq> getAll();



}
