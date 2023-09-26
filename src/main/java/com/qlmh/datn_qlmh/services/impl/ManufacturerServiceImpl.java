package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ManufacturerReq;
import com.qlmh.datn_qlmh.dtos.response.ManufacturerResp;
import com.qlmh.datn_qlmh.entities.ManufacturerEntity;
import com.qlmh.datn_qlmh.repositories.ManufacturerRepo;
import com.qlmh.datn_qlmh.services.ManufacturerService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    ManufacturerRepo manufacturerRepo;
    @Autowired
    Mapper mapper;

    @Override
    public ManufacturerReq create(ManufacturerReq manufacturerReq) {
        ManufacturerEntity manufacturerEntity = mapper.toManufacturerEntity(manufacturerReq);
        manufacturerEntity = manufacturerRepo.save(manufacturerEntity);
        return mapper.toManufacturerReq(manufacturerEntity);
    }

    @Override
    public ManufacturerReq update(ManufacturerReq manufacturerReq) {
        if (manufacturerReq.getId() == null) {
            return null;
        }
        ManufacturerEntity manufacturerEntity = mapper.toManufacturerEntity(manufacturerReq);
        manufacturerEntity = manufacturerRepo.save(manufacturerEntity);
        return mapper.toManufacturerReq(manufacturerEntity);
    }

    @Override
    public List<ManufacturerResp> getALl() {
        return manufacturerRepo.findAll().stream().map((entity) -> mapper.toManufacturerResp(entity)).collect(Collectors.toList());
    }

    @Override
    public ManufacturerReq getByID(Integer id) {
       Optional<ManufacturerEntity>  optional  = manufacturerRepo.findById(id);
       if (optional.isPresent()){
           return mapper.toManufacturerReq(optional.get());
       }
       return null;
    }
}
