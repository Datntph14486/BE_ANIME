package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ExchangeProductImgReq;

import java.util.List;

public interface ExchangeProductImgService {
     ExchangeProductImgReq create(ExchangeProductImgReq exchangeProductImgReq);
     ExchangeProductImgReq update(ExchangeProductImgReq exchangeProductImgReq);
    List<ExchangeProductImgReq> getByRequestId (Integer id);
    List<ExchangeProductImgReq> getAll();
}
