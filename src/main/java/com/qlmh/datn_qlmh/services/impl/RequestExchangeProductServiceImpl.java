package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.RequestExchangeProductReq;
import com.qlmh.datn_qlmh.entities.ExchangeProductEntity;
import com.qlmh.datn_qlmh.entities.RequestExchangeProductEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.RequestExchangeRepo;
import com.qlmh.datn_qlmh.services.RequestExchangeProductService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RequestExchangeProductServiceImpl implements RequestExchangeProductService {

    @Autowired
    Mapper mapper;

    @Autowired
    RequestExchangeRepo requestExchangeRepo;
    @Override
    public RequestExchangeProductReq create(RequestExchangeProductReq requestExchangeProductReq) {

        RequestExchangeProductEntity exchangeProductEntity = mapper.toRequestExchangeProductEntity(requestExchangeProductReq);
        requestExchangeRepo.save(exchangeProductEntity);

        return mapper.toRequestExchangeProductReq(exchangeProductEntity);
    }

    @Override
    public RequestExchangeProductReq update(RequestExchangeProductReq requestExchangeProductReq) {
        if (requestExchangeProductReq.getId() == null) {
            throw new InvalidArgumentException("id is not null ");
        }
        RequestExchangeProductEntity requestExchangeProductEntity = requestExchangeRepo.findById(requestExchangeProductReq.getId()).orElseThrow(() -> new NotFoundException("rate not found : " + requestExchangeProductReq.getId()));
        requestExchangeProductEntity = mapper.toRequestExchangeProductEntity(requestExchangeProductReq);
        requestExchangeRepo.save(requestExchangeProductEntity);
        return mapper.toRequestExchangeProductReq(requestExchangeProductEntity);
    }

    @Override
    public List<RequestExchangeProductReq> getAll() {
        return requestExchangeRepo.findAll().stream().map((entity) -> mapper.toRequestExchangeProductReq(entity)).collect(Collectors.toList());
    }
}
