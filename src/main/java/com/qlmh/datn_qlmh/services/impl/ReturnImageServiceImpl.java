package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ReturnImageReq;
import com.qlmh.datn_qlmh.entities.ReturnImageEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ReturnImageRepo;
import com.qlmh.datn_qlmh.services.ReturnImageService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnImageServiceImpl implements ReturnImageService {

    @Autowired
    ReturnImageRepo imgReturnRepo;
    @Autowired
    Mapper mapper;
    private ReturnImageReq imgReturnReq;

    @Override
    public ReturnImageReq getById(Integer imgId) {
        ReturnImageEntity returnImageEntity = imgReturnRepo.findById(imgId).orElseThrow(() -> new NotFoundException("Return img not found : " + imgId));
        return mapper.toImgReturnReq(returnImageEntity);
    }

    @Override
    public ReturnImageReq create(ReturnImageReq imgReturnReq) {
        ReturnImageEntity returnImageEntity = mapper.toImageReturnEntity((imgReturnReq));
        imgReturnRepo.save(returnImageEntity);
        return imgReturnReq;
    }

    @Override
    public ReturnImageReq update(ReturnImageReq imgReturnReq) {
      if (imgReturnReq.getId()==null) throw new InvalidArgumentException("id is not null ");
      ReturnImageEntity returnImageEntity = imgReturnRepo.findById(imgReturnReq.getId()).orElseThrow(() -> new NotFoundException("ImgReturn not found : " + imgReturnReq.getId()));
      returnImageEntity = mapper.toImageReturnEntity(imgReturnReq);
      imgReturnRepo.save(returnImageEntity);
      return mapper.toImgReturnReq(returnImageEntity);
    }

    @Override
    public List<ReturnImageReq> getByRequestId(Integer requestId) {
        return null;
    }
}
