package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.dtos.request.RateImgReq;
import com.qlmh.datn_qlmh.entities.RateImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RateImgRepo extends JpaRepository<RateImgEntity,Integer> {
   @Query("SELECT r FROM RateImgEntity r WHERE r.rateID=?1")
    List<RateImgEntity> findByRate(Integer rateID);



}
