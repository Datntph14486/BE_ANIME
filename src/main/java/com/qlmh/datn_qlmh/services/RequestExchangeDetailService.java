package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.RequestExchangeProductDetailReq;

import java.util.List;

public interface RequestExchangeDetailService {
    RequestExchangeProductDetailReq create(RequestExchangeProductDetailReq requestExchangeProductDetailReq);

    RequestExchangeProductDetailReq update(RequestExchangeProductDetailReq requestExchangeProductDetailReq);

    List<RequestExchangeProductDetailReq> getAll();

    List<RequestExchangeProductDetailReq> getByRequestId(Integer id);
}
