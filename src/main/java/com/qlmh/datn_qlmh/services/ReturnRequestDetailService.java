package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ReturnFigureReq;
import com.qlmh.datn_qlmh.dtos.request.ReturnRequestDetailReq;

import java.util.List;

public interface ReturnRequestDetailService {
    ReturnRequestDetailReq create (ReturnRequestDetailReq returnRequestDetailReq);
    ReturnRequestDetailReq update(ReturnRequestDetailReq returnRequestDetailReq);
    List<ReturnRequestDetailReq> getAll();
    ReturnRequestDetailReq getById(Integer id);
}
