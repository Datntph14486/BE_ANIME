//package com.qlmh.datn_qlmh.controllers;
//
//import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
//import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
//import com.qlmh.datn_qlmh.services.DiscountService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/black-list")
//public class BlackListController {
//    @Autowired
//    private DiscountService discountService;
//    @GetMapping("/get-all")
//    public ResponseEntity<List<DiscountReq>> getAll() {
//        return ResponseEntity.ok(discountService.findAll());
//    }
//    @GetMapping("/get/{id}")
//    public ResponseEntity<DiscountReq> getById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(discountService.findById(id));
//    }
//    @PostMapping("/new")
//    public ResponseEntity<DiscountReq> addDiscount(@RequestBody DiscountReq DiscountReq) {
//        return ResponseEntity.ok(discountService.save(DiscountReq));
//    }
//    @PutMapping("/update")
//    public ResponseEntity<DiscountReq> updateDiscount(@RequestBody DiscountReq DiscountReq) {
//        return ResponseEntity.ok(discountService.update(DiscountReq));
//    }
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ErrorResponse> delete(@PathVariable("id") Long id) {
//        discountService.delete(id);
//        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "Discount has been deleted"));
//    }
//}
