package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.ProductRateReq;
import com.qlmh.datn_qlmh.services.ProductRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/product-rate")
public class ProductRateController {
    @Autowired
    ProductRateService productRateService;

    @GetMapping("/get-by-productid")
    public ProductRateReq getByProductID(@RequestParam("id") Integer productId){
        return productRateService.findProdcutID(productId);
    }

   @PostMapping("/create")
    public ProductRateReq create(@RequestBody ProductRateReq productRateReq){
       return productRateService.create(productRateReq);
   }

    @PutMapping("/update")
    public ProductRateReq update(@RequestBody ProductRateReq productRateReq){
        return productRateService.update(productRateReq);
    }

    @GetMapping("/start")
    public double start(@RequestParam("id") Integer productId){
        return productRateService.getStartOfProduct(productId);
    }
}
