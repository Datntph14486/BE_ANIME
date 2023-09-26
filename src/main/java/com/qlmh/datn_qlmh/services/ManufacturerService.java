package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ManufacturerReq;
import com.qlmh.datn_qlmh.dtos.response.ManufacturerResp;

import java.util.List;

public interface ManufacturerService {
    ManufacturerReq create(ManufacturerReq manufacturerReq);
    ManufacturerReq update(ManufacturerReq manufacturerReq);
    List<ManufacturerResp> getALl();
    ManufacturerReq getByID(Integer id);
}
