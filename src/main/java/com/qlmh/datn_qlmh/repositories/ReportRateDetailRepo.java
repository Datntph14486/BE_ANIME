package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.ReportDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRateDetailRepo extends JpaRepository<ReportDetailEntity, Integer> {
    @Query("SELECT p FROM ReportDetailEntity p WHERE p.createBy=?1 and p.reportRateId=?2")
    List<ReportDetailEntity> findByUsername(String name, Integer rateId);
    @Query("SELECT p FROM ReportDetailEntity p WHERE  p.reportRateId=?1")
    List<ReportDetailEntity> findByReprtId(Integer rateID);
}
