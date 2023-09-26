package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ReturnRequestDetailReq;
import com.qlmh.datn_qlmh.entities.ReturnRequestDetailEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ReturnRequestDetailRepo;
import com.qlmh.datn_qlmh.services.ReturnRequestDetailService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnRequestDetailServiceImpl implements ReturnRequestDetailService {

    @Autowired
    ReturnRequestDetailRepo returnRequestDetailRepo;
    @Autowired
    Mapper returnMapper;

    @Override
    public ReturnRequestDetailReq create(ReturnRequestDetailReq returnRequestDetailReq) {
        ReturnRequestDetailEntity returnRequestDetailEntity = returnMapper.toReturnRequestDetailEntity(returnRequestDetailReq);
        returnRequestDetailEntity = returnRequestDetailRepo.save(returnRequestDetailEntity);
        return returnMapper.toReturnRequestDetailReq(returnRequestDetailEntity);
    }

    @Override
    public ReturnRequestDetailReq update(ReturnRequestDetailReq returnRequestDetailReq) {
      if (returnRequestDetailReq.getId() == null) throw new InvalidArgumentException("id is not null");
        ReturnRequestDetailEntity returnRequestDetailEntity = returnRequestDetailRepo.findById(returnRequestDetailReq.getId()).orElseThrow(() -> new NotFoundException("Not found : " + returnRequestDetailReq.getId()));
        returnRequestDetailEntity = returnMapper.toReturnRequestDetailEntity(returnRequestDetailReq);
        returnRequestDetailEntity = returnRequestDetailRepo.save(returnRequestDetailEntity);
        return returnMapper.toReturnRequestDetailReq(returnRequestDetailEntity);
    }

    @Override
    public List<ReturnRequestDetailReq> getAll() {
        return returnRequestDetailRepo.findAll().stream().map((entity) -> returnMapper.toReturnRequestDetailReq(entity)).collect(Collectors.toList());
    }

    @Override
    public ReturnRequestDetailReq getById(Integer id) {
       ReturnRequestDetailEntity requestDetailEntity = returnRequestDetailRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found: " + id));
        return returnMapper.toReturnRequestDetailReq(requestDetailEntity);
    }
}
