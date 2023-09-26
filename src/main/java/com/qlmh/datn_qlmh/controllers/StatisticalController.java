package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.response.StatisByDayRes;
import com.qlmh.datn_qlmh.dtos.response.StatisDetailRes;
import com.qlmh.datn_qlmh.dtos.response.StatisInfoDetailRes;
import com.qlmh.datn_qlmh.dtos.response.StatisRes;
import com.qlmh.datn_qlmh.dtos.response.StatisReveRes;
import com.qlmh.datn_qlmh.dtos.response.TopSaleRes;
import com.qlmh.datn_qlmh.entities.DetailBillEntity;
import com.qlmh.datn_qlmh.repositories.StatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/stats")
public class StatisticalController {
    @Autowired
    StatsRepo statsRepo;

    @GetMapping("/doanhThuChonNam")
    public List<StatisReveRes> getAll2(@RequestParam Integer year){
        return statsRepo.getTotal(year);
    }
    @GetMapping("/revenuebydate")
    public List<StatisRes> getTotalByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return statsRepo.getTotalByDate(startDate,endDate);
    }
    @GetMapping("/revenuebyday")
    public List<StatisByDayRes> getTotalByDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return statsRepo.getTotalByDay(startDate,endDate);
    }
    @GetMapping("/getAllDay")
    public List<StatisByDayRes> getAllDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return statsRepo.getDayOfStat(startDate,endDate);
    }
    @GetMapping("/top")
    public List<TopSaleRes> getTopSale(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Pageable pageable = PageRequest.of(0, limit);
//        ArrayList<TopSaleRes> result = statsRepo.getTopSale(startDate,endDate);
//        return result.subList(0, Math.min(limit, result.size()));
        return statsRepo.getTopSale(startDate,endDate);
    }
    @GetMapping("/statisDetail")
    public List<StatisDetailRes> getSta(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, @RequestParam int limit) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Pageable pageable = PageRequest.of(0, limit);
        ArrayList<StatisDetailRes> result = statsRepo.getDetailBill(startDate,endDate, pageable);
        return result.subList(0, Math.min(limit, result.size()));
    }
    @GetMapping("/inDetail")
    public List<StatisInfoDetailRes> getInfoDetail(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Pageable pageable = PageRequest.of(0, limit);
//        ArrayList<StatisInfoDetailRes> result = statsRepo.getInfoDetailBill(startDate,endDate);
//        return result.subList(0, Math.min(limit, result.size()));
        return statsRepo.getInfoDetailBill(startDate,endDate);
    }
//    getInfoDetailBill
    @GetMapping("/doanhthu")
    public List<StatisReveRes> getStatisReveRes(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        return statsRepo.getStatisReveRes(startDate,endDate);
    }
    //push
}
