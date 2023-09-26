package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import com.qlmh.datn_qlmh.dtos.request.ProductDiscountDto;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import com.qlmh.datn_qlmh.services.DiscountProductService;
import com.qlmh.datn_qlmh.services.DiscountService;
import jakarta.annotation.security.RolesAllowed;
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
@RequestMapping("/api/discount")
@CrossOrigin("*")
public class DiscountController {
    @Autowired
    private DiscountService discountService;
    @Autowired
    private DiscountProductService discountProductService;
//    @RolesAllowed({Constant.AccountRole.USER,Constant.AccountRole.ADMIN})
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
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountService.findAll( pageable,  search,  status,  from,  to)));

    }

//    @RolesAllowed({Constant.AccountRole.ADMIN})
    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountService.findById(id)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getDiscountById(@PathVariable("id") Long id,
                                                    @RequestParam(name = "page",required = false,defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size",required = false,defaultValue = "2" )Integer pageSize,
                                                    @RequestParam(name = "categories",required = false,defaultValue = "" )Integer[] category,
                                                    @RequestParam(name = "name",required = false,defaultValue = "" )String name) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountProductService.getById(id,pageable,category,name)));
    }
    @PostMapping("/product/save")
    public ResponseEntity<Response> saveProductIntoDiscount(@RequestBody ProductDiscountDto productDiscountDto){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountProductService.save(productDiscountDto)));
    }
    @PostMapping("/product/remove")
    public ResponseEntity<Response> removeProductIntoDiscount(@RequestBody ProductDiscountDto productDiscountDto){
        productDiscountDto.setStatus(false);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountProductService.remove(productDiscountDto)));
    }
    @PostMapping("/product/save-all")
    public ResponseEntity<Response> saveAllProductIntoDiscount(@RequestBody List<ProductDiscountDto> productDiscountDto){
        System.out.println("logger");
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountProductService.saveAll(productDiscountDto)));
    }
    @PostMapping("/product/remove-all")
    public ResponseEntity<Response> removeAllProductIntoDiscount(@RequestBody List<ProductDiscountDto> productDiscountDto){
        System.out.println("logger");
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountProductService.removeAll(productDiscountDto)));
    }
    @PostMapping("/new")
    public ResponseEntity<Response> addDiscount(@Valid @RequestBody DiscountReq discountReq) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountService.save(discountReq)));
    }
    @PutMapping("/update")
    public ResponseEntity<Response> updateDiscount(@Valid @RequestBody DiscountReq discountReq) {
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.SUCCESS,discountService.update(discountReq)));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id) {
        discountService.delete(id);
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), new ErrorResponse(HttpStatus.OK.value(), "Discount has been deleted")));

    }
}
