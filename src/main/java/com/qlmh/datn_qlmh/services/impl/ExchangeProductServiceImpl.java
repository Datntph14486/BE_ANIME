package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ExchangeProductReq;
import com.qlmh.datn_qlmh.entities.ExchangeProductEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;

import com.qlmh.datn_qlmh.repositories.EchangeProductRepo;
import com.qlmh.datn_qlmh.repositories.ExchangeProductImgRepo;
import com.qlmh.datn_qlmh.services.ExchangeProductService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ExchangeProductServiceImpl implements ExchangeProductService {

    @Autowired
    EchangeProductRepo exchangeProductRepo;

    @Autowired
    Mapper mapper;

    @Override
    public ExchangeProductReq create(ExchangeProductReq exchangeProductReq) {
        ExchangeProductEntity exchangeProductEntity = mapper.toExchangeProductEntity(exchangeProductReq);
        exchangeProductRepo.save(exchangeProductEntity);
        return mapper.toExchangeProductReq(exchangeProductEntity);
    }

    @Override
    public ExchangeProductReq update(ExchangeProductReq exchangeProductReq) {
        if (exchangeProductReq.getId() == null) {
            throw new InvalidArgumentException("id is not null ");
        }
        ExchangeProductEntity exchangeProductEntity = exchangeProductRepo.findById(exchangeProductReq.getId()).orElseThrow(() -> new NotFoundException("rate not found : " + exchangeProductReq.getId()));
        exchangeProductEntity = mapper.toExchangeProductEntity(exchangeProductReq);
        exchangeProductRepo.save(exchangeProductEntity);
        return mapper.toExchangeProductReq(exchangeProductEntity);
    }

    @Override
    public List<ExchangeProductReq> getAll() {
        return exchangeProductRepo.findAll().stream().map((entity) -> mapper.toExchangeProductReq(entity)).collect(Collectors.toList());
    }
}
