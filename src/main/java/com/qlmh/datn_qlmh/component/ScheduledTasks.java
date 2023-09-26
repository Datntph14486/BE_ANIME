package com.qlmh.datn_qlmh.component;

import com.qlmh.datn_qlmh.entities.BillEntity;
import com.qlmh.datn_qlmh.entities.DeliveryInvoiceEntity;
import com.qlmh.datn_qlmh.entities.DetailCartEntity;
import com.qlmh.datn_qlmh.entities.RateEntity;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.*;
import com.qlmh.datn_qlmh.services.RateService;
import com.qlmh.datn_qlmh.services.impl.BillServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Log4j2
@EnableScheduling
public class ScheduledTasks {
    private final BillRepo billRepo;
    private final DetailBillRepo detailBillRepo;
    private final DeliveryInvoiceRepo deliveryInvoiceRepo;
    private final ProductDiscountRepo productDiscountRepo;
    private final RestTemplate restTemplate;
    private final BillServiceImpl billService;
    private final CartRepo cartRepo;
    private final DetailCartRepo detailCartRepo;
    private final RateService  rateService;

    private final RateRepo rateRepo;

    @Autowired
    public ScheduledTasks(BillRepo billRepo, DetailBillRepo detailBillRepo, DeliveryInvoiceRepo deliveryInvoiceRepo,
                          ProductDiscountRepo productDiscountRepo, RestTemplate restTemplate, BillServiceImpl billService, CartRepo cartRepo, DetailCartRepo detailCartRepo, RateService rateService, RateRepo rateRepo) {
        this.billRepo = billRepo;
        this.detailBillRepo = detailBillRepo;
        this.deliveryInvoiceRepo = deliveryInvoiceRepo;
        this.productDiscountRepo = productDiscountRepo;
        this.restTemplate = restTemplate;
        this.billService = billService;
        this.cartRepo = cartRepo;
        this.detailCartRepo = detailCartRepo;
        this.rateService = rateService;
        this.rateRepo = rateRepo;
    }

    @Scheduled(cron = "0 0 0 * * ?") // chạy lúc 12h đêm
    public void deleteOldDetailCarts() {
        log.info("deleteOldDetailCarts :: Start");
        Date now = new Date();
        long timeThreshold = 30 * 24 * 60 * 60 * 1000L;
        Date threshold = new Date(now.getTime() - timeThreshold);
        List<DetailCartEntity> oldDetailCarts = detailCartRepo.findByCreateDateBefore(threshold);
        detailCartRepo.deleteAll(oldDetailCarts);
        log.info("deleteOldDetailCarts :: End");
    }
    @Scheduled(fixedDelay = 3600000) // Chạy sau mỗi 1 giờ
    public void checkOrders() {
        log.info("checkOrders :: Start");
        List<BillEntity> orders = billRepo.findAll();

        for (BillEntity order : orders) {
            if (order.getPaymentMethod() == BillEntity.PaymentMethod.CARD &&order.getCustomerMoney()== BigDecimal.valueOf(0)|| order.getCustomerMoney()== null
                    && order.getCreateDate().getTime() < (new Date().getTime() - 24 * 60 * 60 * 1000)) {
                billService.update(order.getId(), BillEntity.StatusEnum.HUY);
            }
        }
        log.info("checkOrders :: End");
    }


    @Scheduled(fixedDelay = 86400000) // Chạy sau mỗi 24 giờ
    public void checkRates() {
        log.info("checkRates :: Start");
       List<RateEntity> rates = rateRepo.findAll();

        for (RateEntity rate : rates) {
            if(rate.getCreateDate().getTime() < (new Date().getTime() - 7 * 24 * 60 * 60 * 1000)){
                rate.setRightToEdit(true);
                rateRepo.save(rate);
            }
        }
        log.info("checkRates :: End");
    }



}
