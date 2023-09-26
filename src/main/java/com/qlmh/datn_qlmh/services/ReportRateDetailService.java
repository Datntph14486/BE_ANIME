package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ReportRateDetailReq;

import com.qlmh.datn_qlmh.dtos.response.ReportRateDetailRes;

import org.springframework.security.core.Authentication;
public interface ReportRateDetailService {
    public ReportRateDetailRes addReport(Authentication authentication,ReportRateDetailReq reportRateDetailReq);
    ReportRateDetailRes deleteDetailReport(Integer reportId);


}
