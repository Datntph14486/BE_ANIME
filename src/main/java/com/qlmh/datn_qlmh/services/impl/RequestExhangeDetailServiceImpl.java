package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.RequestExchangeProductDetailReq;
import com.qlmh.datn_qlmh.entities.RateEntity;
import com.qlmh.datn_qlmh.entities.RequestExchangeProductDetailEntity;
import com.qlmh.datn_qlmh.entities.RequestExchangeProductEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.RequestExchangeDetailRepo;
import com.qlmh.datn_qlmh.services.RequestExchangeDetailService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RequestExhangeDetailServiceImpl implements RequestExchangeDetailService {

    @Autowired
    Mapper mapper;

    @Autowired
    RequestExchangeDetailRepo requestExchangeDetailRepo;

    @Override
    public RequestExchangeProductDetailReq create(RequestExchangeProductDetailReq requestExchangeProductDetailReq) {
        RequestExchangeProductDetailEntity entity = mapper.toRequestExchangeProductDetailEntity(requestExchangeProductDetailReq);
        requestExchangeDetailRepo.save(entity);
        return mapper.toRequestExchangeProductDetailReq(entity);
    }

    @Override
    public RequestExchangeProductDetailReq update(RequestExchangeProductDetailReq requestExchangeProductDetailReq) {
        if (requestExchangeProductDetailReq.getId() == null) {
            throw new InvalidArgumentException("id is not null ");
        }
        RequestExchangeProductDetailEntity requestExchangeProductEntity = requestExchangeDetailRepo.findById(requestExchangeProductDetailReq.getId()).orElseThrow(() -> new NotFoundException(" not found : " + requestExchangeProductDetailReq.getId()));
        requestExchangeProductEntity = mapper.toRequestExchangeProductDetailEntity(requestExchangeProductDetailReq);
        requestExchangeDetailRepo.save(requestExchangeProductEntity);
        return mapper.toRequestExchangeProductDetailReq(requestExchangeProductEntity);
    }

    @Override
    public List<RequestExchangeProductDetailReq> getAll() {
        return requestExchangeDetailRepo.findAll().stream().map((entity) -> mapper.toRequestExchangeProductDetailReq(entity)).collect(Collectors.toList());
    }

    @Override
    public List<RequestExchangeProductDetailReq> getByRequestId(Integer id) {
        List<RequestExchangeProductDetailEntity> list = requestExchangeDetailRepo.getByRequestId(id);
        return list.stream().map((entity) -> mapper.toRequestExchangeProductDetailReq(entity)).collect(Collectors.toList());

    }
}
