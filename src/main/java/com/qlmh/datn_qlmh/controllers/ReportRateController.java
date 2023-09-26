package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ReportRateDetailReq;
import com.qlmh.datn_qlmh.dtos.request.ReportRateReq;
import com.qlmh.datn_qlmh.dtos.response.ReportRateAdminRes;
import com.qlmh.datn_qlmh.dtos.response.ReportRateDetailRes;
import com.qlmh.datn_qlmh.dtos.response.ReportRateRes;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.services.ReportRateDetailService;
import com.qlmh.datn_qlmh.services.ReportRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/report")
public class ReportRateController {

    @Autowired
    ReportRateService reportRateService;

    @Autowired
    ReportRateDetailService reportRateDetailService;
    @PostMapping("add")
    public ReportRateRes addReport(@RequestBody ReportRateReq reportRateReq){
        return reportRateService.addReport(reportRateReq);
    }

    @GetMapping("get-by-rate")
    public ReportRateRes getByRate(@RequestParam("rate-id") Integer rateId){
        return reportRateService.getByRateID(rateId);
    }

    @PostMapping("add-detail")
    public ReportRateDetailRes addDetailReport(Authentication authentication,@RequestBody ReportRateDetailReq reportRateDetailReq){
        return reportRateDetailService.addReport(authentication,reportRateDetailReq);
    }

    @GetMapping("get-report-admin")
    public List<ReportRateAdminRes> getAllreportAdmin(){
        return reportRateService.getAllreportAdmin();
    }

    @DeleteMapping("delete-report-admin")
    public ReportRateRes deleteReportAdmin(@RequestParam("rate-id") Integer rateId){
      return  reportRateService.deleteReport(rateId);
    }
    @DeleteMapping("delete-report-detail-admin")
    public ReportRateDetailRes deleteReportDetailAdmin(@RequestParam("report-id") Integer rateId){
        return  reportRateDetailService.deleteDetailReport(rateId);
    }
}
