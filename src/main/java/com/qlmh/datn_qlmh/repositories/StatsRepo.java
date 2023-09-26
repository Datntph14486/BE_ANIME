package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.dtos.response.StatisByDayRes;
import com.qlmh.datn_qlmh.dtos.response.StatisDetailRes;
import com.qlmh.datn_qlmh.dtos.response.StatisInfoDetailRes;
import com.qlmh.datn_qlmh.dtos.response.StatisRes;
import com.qlmh.datn_qlmh.dtos.response.StatisReveRes;
import com.qlmh.datn_qlmh.dtos.response.TopSaleRes;
import com.qlmh.datn_qlmh.entities.DetailBillEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public interface StatsRepo extends JpaRepository<DetailBillEntity, Integer> {
    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisReveRes((b.total + b.transportFee),sum(p.price * p.amount), (b.total - sum(p.price * p.amount)),month(b.createDate),sum(p.amount)) from BillEntity b JOIN DetailBillEntity p on b.id = p.billId " +
            "where year(b.createDate) = (?1) and b.status = 'DA_GIAO'" +
            "group by month(b.createDate), b.id")
    ArrayList<StatisReveRes> getTotal(Integer startDate);

    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisRes(sum(po.price * po.amount),sum(po.priceSale * po.amount), month(po.createDate), sum((po.priceSale * po.amount) - (po.price * po.amount)),sum(po.amount)) from DetailBillEntity po JOIN BillEntity b ON po.billId = b.id " +
            "where date(po.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'" +
            "group by month(po.createDate) order by  month(po.createDate)")
    ArrayList<StatisRes> getTotalByDate(LocalDate startDate, LocalDate endDate);

    //doanh thu chọn theo tháng (old)
    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisByDayRes(DATE(po.createDate),sum(po.priceSale * po.amount), sum(po.price * po.amount), sum((po.priceSale * po.amount) - (po.price * po.amount)),sum(po.amount)) from DetailBillEntity po JOIN BillEntity b ON po.billId = b.id " +
            "where DATE(po.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'" +
            "group by DATE(po.createDate) order by  DATE(po.createDate) ASC")
    ArrayList<StatisByDayRes> getTotalByDay(LocalDate startDate, LocalDate endDate);

    //hiển thị các ngày trong tháng chọn (new)
    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisByDayRes(date(b.createDate),(b.total + b.transportFee),sum(p.price * p.amount), (b.total - sum(p.price * p.amount)),sum(p.amount)) from BillEntity b JOIN DetailBillEntity p on b.id = p.billId " +
            "where date(b.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'" +
            "group by date(b.createDate), b.id")
    ArrayList<StatisByDayRes> getDayOfStat(LocalDate startDate, LocalDate endDate);


    //bảng chi tiết theo ngày (old)
    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisDetailRes(d.productId,p.figure,d.createDate,d.createBy,d.updateDate,d.updateBy,d.amount, d.price,d.priceSale, i.url) FROM DetailBillEntity d JOIN BillEntity b ON d.billId = b.id JOIN ProductEntity p " +
            "ON d.productId = p.id JOIN ImageEntity i ON i.productID = d.productId "+
            "WHERE date(d.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'"+
            "order by d.createDate desc ")
    ArrayList<StatisDetailRes> getDetailBill(LocalDate startDate, LocalDate endDate,Pageable limit);

    //bảng chi tiết theo ngày (new)
    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisInfoDetailRes(p.figure,b.createDate,b.updateDate,b.createBy,cast(d.amount as integer) ,d.priceSale,cast((d.amount * d.priceSale) As bigdecimal)) FROM DetailBillEntity d JOIN BillEntity b ON d.billId = b.id JOIN ProductEntity p " +
            "ON d.productId = p.id "+
            "WHERE date(d.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'"+
            "group by date(d.createDate), d.productId, b.createDate, b.id, d.priceSale, d.amount "+
            "order by date(b.createDate) desc ")
    ArrayList<StatisInfoDetailRes> getInfoDetailBill(Date startDate, Date endDate);
    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.StatisReveRes((b.total + b.transportFee),sum(p.price * p.amount), (b.total - sum(p.price * p.amount)),month(b.createDate),sum(p.amount)) from BillEntity b JOIN DetailBillEntity p on b.id = p.billId " +
            "where date(b.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'" +
            "group by month(b.createDate), b.id")
    ArrayList<StatisReveRes> getStatisReveRes(LocalDate startDate, LocalDate endDate);

    @Query("SELECT NEW com.qlmh.datn_qlmh.dtos.response.TopSaleRes(d.productId,p.figure, sum(d.amount),d.priceSale , cast(sum(d.priceSale * d.amount) as bigdecimal) ) FROM DetailBillEntity d JOIN BillEntity b ON d.billId = b.id JOIN ProductEntity p " +
            "ON d.productId = p.id "+
            "WHERE date(d.createDate) between (?1) and (?2) and b.status = 'DA_GIAO'"+
            "group by d.productId,d.priceSale ORDER BY SUM(d.priceSale * d.amount) DESC")
    ArrayList<TopSaleRes> getTopSale(LocalDate startDate, LocalDate endDate);


}
