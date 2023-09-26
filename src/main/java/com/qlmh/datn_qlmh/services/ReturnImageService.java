package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ReturnImageReq;

import java.util.List;

public interface ReturnImageService {
    public ReturnImageReq getById(Integer imgId);
    public ReturnImageReq create(ReturnImageReq imgReturnReq);
    public ReturnImageReq update (ReturnImageReq imgReturnReq);
    public List<ReturnImageReq> getByRequestId (Integer requestId);
}
