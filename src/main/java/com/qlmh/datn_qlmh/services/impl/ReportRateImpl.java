package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ReportRateReq;
import com.qlmh.datn_qlmh.dtos.response.RateRes;
import com.qlmh.datn_qlmh.dtos.response.ReportRateAdminRes;
import com.qlmh.datn_qlmh.dtos.response.ReportRateDetailRes;
import com.qlmh.datn_qlmh.dtos.response.ReportRateRes;
import com.qlmh.datn_qlmh.entities.*;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.*;
import com.qlmh.datn_qlmh.services.ReportRateService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReportRateImpl implements ReportRateService {
    @Autowired
    ReportRateDetailRepo reportRateDetailRepo;
    @Autowired
    ReportRateRepo reportRateRepo;
    @Autowired
    RateRepo rateRepo;
    @Autowired
    RateImgRepo rateImgRepo;
    @Autowired
    Mapper mapper;
    @Autowired
    ProductRepo productRepo;

    @Override
    public ReportRateRes addReport(ReportRateReq reportRateReq) {
        if(reportRateReq.getRateID()==null){
            throw new NotFoundException("rateid is null");
        }
        List<ReportRateEntity> reports = reportRateRepo.findByRateID(reportRateReq.getRateID());
        if(reports.size()>0){
            reports.get(0).setTotalReport( reports.get(0).getTotalReport()+1);
            reportRateRepo.save(reports.get(0));
            ReportRateRes reportRateRes = new ReportRateRes();
            reportRateRes.setTotalReport(reports.get(0).getTotalReport());
            reportRateRes.setRateID(reports.get(0).getRateID());
            reportRateRes.setId(reports.get(0).getId());
            return reportRateRes;
        }else{
            ReportRateEntity reportRateEntity = new ReportRateEntity();
            reportRateEntity.setTotalReport(1);
            reportRateEntity.setRateID(reportRateReq.getRateID());
            ReportRateEntity reportRateEntity1   = reportRateRepo.save(reportRateEntity);
           ReportRateRes reportRateRes = new ReportRateRes();
            reportRateRes.setTotalReport(1);
            reportRateRes.setId(reportRateEntity1.getId());
            reportRateRes.setRateID(reportRateEntity1.getRateID());
            return reportRateRes;
        }

    }

    @Override
    public ReportRateRes getByRateID(Integer id) {
        List<ReportRateEntity> reports = reportRateRepo.findByRateID(id);
        if(reports.size() == 0){
            throw new NotFoundException("khong co report ");
        }
        ReportRateRes rateRes =new ReportRateRes();
        rateRes.setId(reports.get(0).getId());
        rateRes.setRateID(reports.get(0).getRateID());
        rateRes.setTotalReport(reports.get(0).getTotalReport());
        return rateRes;
    }

    @Override
    public ReportRateRes deleteReport(Integer id) {
        List<ReportRateEntity> rates =  reportRateRepo.findByRateID(id);
        reportRateRepo.delete(rates.get(0));
        ReportRateRes reportRateRes= new ReportRateRes();
        reportRateRes.setTotalReport(rates.get(0).getTotalReport());
        reportRateRes.setRateID(rates.get(0).getRateID());
        reportRateRes.setId(rates.get(0).getId());
        return reportRateRes;
    }

    @Override
    public  List<ReportRateAdminRes> getAllreportAdmin() {
        List<ReportRateAdminRes> reportRateAdminRes= new ArrayList<>();
        List<ReportRateEntity> reports = reportRateRepo.findAll();
        for (int i = 0; i <reports.size() ; i++) {
            ReportRateAdminRes reportRateAdminRe= new ReportRateAdminRes();
            Optional<RateEntity> rate = rateRepo.findById(reports.get(i).getRateID());
           RateRes rateRes = mapper.toRateRes(rate.get());
            List<RateImgEntity> rateImgEntities = rateImgRepo.findByRate(rate.get().getId());
            rateRes.setImgs(rateImgEntities);
            reportRateAdminRe.setRate(rateRes);
            reportRateAdminRe.setToltalreport(reports.get(i).getTotalReport());
            List<ReportDetailEntity> listUserReport =  reportRateDetailRepo.findByReprtId(reports.get(i).getId());
            List<ReportRateDetailRes> reportRateDetailResList = new ArrayList<>();
            Optional<ProductEntity> productEntity= productRepo.findById(rateRes.getProductID());
            reportRateAdminRe.setProductName(productEntity.get().getFigure());
            if(listUserReport.size()>0){
                for (int j = 0; j < listUserReport.size(); j++) {
                    ReportRateDetailRes reportRateDetailRes = new ReportRateDetailRes();
                    reportRateDetailRes.setCreate_by(listUserReport.get(j).getCreateBy());
                    reportRateDetailResList.add(reportRateDetailRes);
                }
                reportRateAdminRe.setListUserreport(reportRateDetailResList);

            }
            reportRateAdminRes.add(reportRateAdminRe);

        }

        return reportRateAdminRes;
    }
}
