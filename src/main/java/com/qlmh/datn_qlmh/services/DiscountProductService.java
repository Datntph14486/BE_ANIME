package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.dtos.request.ProductDiscountDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiscountProductService {
    public PageData<ProductDiscountDto> getById(Long id, Pageable pageable, Integer[] category, String name);
    ProductDiscountDto remove(ProductDiscountDto productDiscountDto);
    ProductDiscountDto save(ProductDiscountDto productDiscountDto);
    List<ProductDiscountDto> removeAll(List<ProductDiscountDto> productDiscountDto);
    List<ProductDiscountDto> saveAll(List<ProductDiscountDto> productDiscountDto);
}
