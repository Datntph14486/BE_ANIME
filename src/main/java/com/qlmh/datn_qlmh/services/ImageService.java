package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ImageReq;
import com.qlmh.datn_qlmh.dtos.response.ImageResp;

import java.util.List;

public interface ImageService {
    List<ImageResp> getAll();
    List<ImageReq> create(List<ImageReq> imageReq);
    List<ImageReq>  update(List<ImageReq> imageReqList);
    ImageReq getById(Integer id);
    ImageReq  update(ImageReq imageReqList);
    void delete(List<ImageReq> imageReqList);
    List<ImageReq> getImageByProductID(Integer productID);
}
