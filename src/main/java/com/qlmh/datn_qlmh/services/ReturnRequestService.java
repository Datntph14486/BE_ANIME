package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ReturnFigureReq;
import com.qlmh.datn_qlmh.dtos.request.ReturnRequestFigureReq;

import java.util.List;

public interface ReturnRequestService {
    ReturnRequestFigureReq create (ReturnRequestFigureReq returnRequestFigureReq);
    ReturnRequestFigureReq update(ReturnRequestFigureReq returnRequestFigureReq);
    ReturnRequestFigureReq updateStatus(ReturnRequestFigureReq returnRequestFigureReq);
    List<ReturnRequestFigureReq> getAll();
    ReturnRequestFigureReq getById(Integer id);
}
