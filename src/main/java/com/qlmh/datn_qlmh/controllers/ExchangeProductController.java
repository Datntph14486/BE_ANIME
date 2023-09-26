package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ExchangeProductReq;
import com.qlmh.datn_qlmh.services.ExchangeProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange-products")
public class ExchangeProductController {

    @Autowired
    ExchangeProductService exchangeProductService;

    @PostMapping("")
    public ExchangeProductReq add(@RequestBody ExchangeProductReq exchangeProductReq){


        return null;
    }


}
