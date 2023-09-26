package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.DiscountPlusReq;
import com.qlmh.datn_qlmh.services.DiscountPlusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("/api/discount-plus")
@CrossOrigin("*")
public class DiscountPlusController {
    @Autowired
    private DiscountPlusService discountPlusService;
    @PostMapping("/save")
    public ResponseEntity<Response> save(@RequestBody DiscountPlusReq discountPlusReq){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountPlusService.save(discountPlusReq)));
    }
    @PostMapping("/update")
    public ResponseEntity<Response> update(@RequestBody DiscountPlusReq discountPlusReq){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountPlusService.update(discountPlusReq)));
    }
}
