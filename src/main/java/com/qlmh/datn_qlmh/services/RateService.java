package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.RateImgReq;
import com.qlmh.datn_qlmh.dtos.request.RateReq;
import com.qlmh.datn_qlmh.dtos.response.RateRes;
import com.qlmh.datn_qlmh.entities.RateEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface RateService {
  public  RateRes create(Authentication authentication,RateReq rateReq);

  public RateRes getByID(Authentication authentication,Integer id);
  public RateRes update(Authentication authentication,RateReq rateReq);
  public List<RateRes> getAll();
  public List<RateRes> getByProductID(Integer productID);

  public List<RateRes> getbyStart(Integer start);

  List<RateRes> getByUsername(Authentication authentication);
  String getUsername(Authentication authentication);

  RateRes delete(Integer id);



}
