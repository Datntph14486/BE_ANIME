package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ReportRateDetailReq;
import com.qlmh.datn_qlmh.dtos.response.ReportRateDetailRes;
import com.qlmh.datn_qlmh.entities.ReportDetailEntity;
import com.qlmh.datn_qlmh.entities.ReportRateEntity;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ReportRateDetailRepo;
import com.qlmh.datn_qlmh.repositories.ReportRateRepo;
import com.qlmh.datn_qlmh.services.ReportRateDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportRateDetailImpl implements ReportRateDetailService {
    @Autowired
    ReportRateDetailRepo reportRateDetailRepo;

    @Autowired
    ReportRateRepo reportRateRepo;
    @Override
    public ReportRateDetailRes addReport(Authentication authentication, ReportRateDetailReq reportRateDetailReq) {
        List<ReportDetailEntity> reportDetailEntity = reportRateDetailRepo.findByUsername(authentication.getName(),reportRateDetailReq.getReportRateId());
        if(reportDetailEntity.size()==0){
            ReportDetailEntity reportDetailEntity1 = new ReportDetailEntity();
            reportDetailEntity1.setReportRateId(reportRateDetailReq.getReportRateId());
            ReportDetailEntity reportDetailEntity2 = reportRateDetailRepo.save(reportDetailEntity1);
            ReportRateDetailRes reportRateDetailRes = new ReportRateDetailRes();
            reportRateDetailRes.setId(reportDetailEntity2.getId());
            reportRateDetailRes.setReportRateId(reportDetailEntity2.getReportRateId());
            reportRateDetailRes.setCreate_by(reportDetailEntity2.getCreateBy());
            return reportRateDetailRes;
        }
   Optional<ReportRateEntity> reportRateEntity = reportRateRepo.findById(reportRateDetailReq.getReportRateId());
        reportRateEntity.get().setTotalReport(reportRateEntity.get().getTotalReport()-1);
        reportRateRepo.save(reportRateEntity.get());
        throw new NotFoundException("Ban da report roi!");
    }

    @Override
    public ReportRateDetailRes deleteDetailReport(Integer reportId) {
      List<ReportDetailEntity> reportDetailEntity =reportRateDetailRepo.findByReprtId(reportId);
        reportRateDetailRepo.deleteAll(reportDetailEntity);
        return null;
    }
}
