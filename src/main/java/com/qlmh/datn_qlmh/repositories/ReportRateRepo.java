package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.RateEntity;
import com.qlmh.datn_qlmh.entities.ReportRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRateRepo extends JpaRepository<ReportRateEntity, Integer> {
    @Query("SELECT p FROM ReportRateEntity p WHERE p.rateID=?1")
    List<ReportRateEntity> findByRateID(Integer rateID);
}
