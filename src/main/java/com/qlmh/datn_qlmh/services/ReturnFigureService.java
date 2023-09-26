package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ReturnFigureReq;

import java.util.List;

public interface ReturnFigureService {

    ReturnFigureReq create (ReturnFigureReq returnReq);
    ReturnFigureReq update(ReturnFigureReq returnReq);
//    ReturnReq updateStatus(ReturnReq returnReq);
    List<ReturnFigureReq> getAll();
    ReturnFigureReq getById(Integer id);
}
