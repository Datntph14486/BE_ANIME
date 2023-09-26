package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.Token;
import com.qlmh.datn_qlmh.dtos.request.VoucherReq;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import com.qlmh.datn_qlmh.services.VoucherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/voucher")
@CrossOrigin("*")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;
    @GetMapping("/get-all")
    public ResponseEntity<Response> getAll(@RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                           @RequestParam(name = "size",required = false,defaultValue = "5" )Integer pageSize,
                                           @RequestParam(name = "search",required = false,defaultValue = "") String search,
                                           @RequestParam(name = "status",required = false) Integer status,
                                           @RequestParam(name = "from",required = false) String from,
                                           @RequestParam(name = "to",required = false) String to
    ) {
        System.out.println(from + " - " + to);
        Sort sort = Sort.by(Sort.Direction.DESC,"discountStart");
        Pageable pageable = PageRequest.of(page, pageSize,sort);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,voucherService.findAll( pageable,  search,  status,  from,  to)));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,voucherService.findById(id)));
    }
    @PostMapping("/get")
    public ResponseEntity<Response> getById(@RequestBody Token token) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,voucherService.get(token.getToken())));
    }
    @PostMapping("/new")
    public ResponseEntity<Response> addVoucher(@Valid @RequestBody VoucherReq voucherReq) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,voucherService.save(voucherReq)));
    }
    @PutMapping("/update")
    public ResponseEntity<Response> updateVoucher(@Valid @RequestBody VoucherReq voucherReq) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,voucherService.update(voucherReq)));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        voucherService.delete(id);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), new ErrorResponse(HttpStatus.OK.value(), "Voucher has been deleted")));
    }
}
