package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.SeriesReq;
import com.qlmh.datn_qlmh.dtos.response.SeriesResp;
import com.qlmh.datn_qlmh.entities.ManufacturerEntity;
import com.qlmh.datn_qlmh.entities.SeriesEntity;
import com.qlmh.datn_qlmh.repositories.SeriesRepo;
import com.qlmh.datn_qlmh.services.SeriesService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesServiceImpl implements SeriesService {
    @Autowired
    SeriesRepo seriesRepo;
    @Autowired
    Mapper mapper;
    @Override
    public SeriesReq create(SeriesReq seriesReq) {
        SeriesEntity seriesEntity = mapper.toSeriesEntiry(seriesReq);
        seriesEntity = seriesRepo.save(seriesEntity);
        return mapper.toSeriesReq(seriesEntity);
    }

    @Override
    public SeriesReq update(SeriesReq seriesReq) {
        if (seriesReq.getId() == null) {
            return null;
        }
        SeriesEntity seriesEntity = mapper.toSeriesEntiry(seriesReq);
        seriesEntity = seriesRepo.save(seriesEntity);
        return mapper.toSeriesReq(seriesEntity);
    }

    @Override
    public List<SeriesResp> getALl() {
        return seriesRepo.findAll().stream().map((entity) -> mapper.toSeriesResp(entity)).collect(Collectors.toList());
    }

    @Override
    public SeriesReq getByID(Integer id) {
        Optional<SeriesEntity> optional  = seriesRepo.findById(id);
        if (optional.isPresent()){
            return mapper.toSeriesReq(optional.get());
        }
        return null;
    }
}
