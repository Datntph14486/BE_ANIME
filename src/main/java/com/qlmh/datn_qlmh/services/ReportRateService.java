package com.qlmh.datn_qlmh.services;


import com.qlmh.datn_qlmh.dtos.request.ReportRateReq;
import com.qlmh.datn_qlmh.dtos.response.ReportRateAdminRes;
import com.qlmh.datn_qlmh.dtos.response.ReportRateRes;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReportRateService {
    public ReportRateRes addReport(ReportRateReq reportRateReq);

    public ReportRateRes getByRateID(Integer id);

    public ReportRateRes  deleteReport(Integer id);

    List<ReportRateAdminRes> getAllreportAdmin();



}
