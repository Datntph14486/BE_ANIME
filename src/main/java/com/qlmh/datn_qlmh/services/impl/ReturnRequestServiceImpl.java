package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ReturnRequestFigureReq;
import com.qlmh.datn_qlmh.entities.ReturnRequestFigureEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ReturnRequestRepo;
import com.qlmh.datn_qlmh.services.ReturnRequestService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

    @Autowired
    ReturnRequestRepo returnRequestRepo;

    @Autowired
    Mapper returnMapper;

    @Override
    public ReturnRequestFigureReq create(ReturnRequestFigureReq returnRequestFigureReq) {
        ReturnRequestFigureEntity returnRequestFigureEntity = returnMapper.toReturnRequestFigureEntity(returnRequestFigureReq);
        returnRequestFigureEntity = returnRequestRepo.save(returnRequestFigureEntity);
        return returnMapper.toReturnRequestFigureReq(returnRequestFigureEntity);
    }

    @Override
    public ReturnRequestFigureReq update(ReturnRequestFigureReq returnRequestFigureReq) {
        if (returnRequestFigureReq.getId() == null) throw new InvalidArgumentException("id is not null ");
        ReturnRequestFigureEntity returnRequestEntity =   returnRequestRepo.findById(returnRequestFigureReq.getId()).orElseThrow(() -> new NotFoundException("Not found : " + returnRequestFigureReq.getId()));
        returnRequestEntity = returnMapper.toReturnRequestFigureEntity(returnRequestFigureReq);
        returnRequestEntity = returnRequestRepo.save(returnRequestEntity);
        return returnMapper.toReturnRequestFigureReq(returnRequestEntity);

    }

    @Override
    public ReturnRequestFigureReq updateStatus(ReturnRequestFigureReq returnRequestFigureReq) {
        return null;
    }

    @Override
    public List<ReturnRequestFigureReq> getAll() {
        return returnRequestRepo.findAll().stream().map((entity) -> returnMapper.toReturnRequestFigureReq(entity)).collect(Collectors.toList());
    }

    @Override
    public ReturnRequestFigureReq getById(Integer id) {
        ReturnRequestFigureEntity returnRequestEntity = returnRequestRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found: " + id));
        return returnMapper.toReturnRequestFigureReq(returnRequestEntity);
    }
}
