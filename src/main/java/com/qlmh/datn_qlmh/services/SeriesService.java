package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ManufacturerReq;
import com.qlmh.datn_qlmh.dtos.request.SeriesReq;
import com.qlmh.datn_qlmh.dtos.response.ManufacturerResp;
import com.qlmh.datn_qlmh.dtos.response.SeriesResp;

import java.util.List;

public interface SeriesService {
    SeriesReq create(SeriesReq seriesReq);
    SeriesReq update(SeriesReq seriesReq);
    List<SeriesResp> getALl();
    SeriesReq getByID(Integer id);
}
