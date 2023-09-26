
package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.DiscountWithBillReq;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import com.qlmh.datn_qlmh.services.DiscountWithBillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/discount-bill")
@CrossOrigin("*")
public class DiscountWithBillController {

    private final DiscountWithBillService discountWithBillService;
    DiscountWithBillController(DiscountWithBillService d){
        this.discountWithBillService = d;
    }
    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll(
            @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
            @RequestParam(name = "size",required = false,defaultValue = "5" )Integer pageSize,
            @RequestParam(name = "search",required = false,defaultValue = "") String search,
            @RequestParam(name = "status",required = false) Integer status,
            @RequestParam(name = "from",required = false) String from,
            @RequestParam(name = "to",required = false) String to
            ) {
        System.out.println(from + " - " + to);
        Sort sort = Sort.by(Sort.Direction.DESC,"discountStart");
        Pageable pageable = PageRequest.of(page, pageSize,sort);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountWithBillService.findAll( pageable,  search,  status,  from,  to)));
    }
    @GetMapping("/get-current")
    public ResponseEntity<Response> get(
    ) {

        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountWithBillService.get()));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountWithBillService.findById(id)));
    }
    @PostMapping("/new")
    public ResponseEntity<Response> addDiscountBill(@Valid @RequestBody DiscountWithBillReq discountWithBillReq) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountWithBillService.save(discountWithBillReq)));
    }
    @PutMapping("/update")
    public ResponseEntity<Response> updateDiscountBill(@Valid @RequestBody DiscountWithBillReq discountWithBillReq) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountWithBillService.update(discountWithBillReq)));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        discountWithBillService.delete(id);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), new ErrorResponse(HttpStatus.OK.value(), "Discount has been deleted")));
    }
}

