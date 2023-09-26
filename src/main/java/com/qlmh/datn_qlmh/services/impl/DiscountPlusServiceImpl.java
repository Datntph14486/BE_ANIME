package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.DiscountPlusReq;
import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import com.qlmh.datn_qlmh.entities.DiscountEntity;
import com.qlmh.datn_qlmh.entities.DiscountPlusEntity;
import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.DiscountPlusRepo;
import com.qlmh.datn_qlmh.repositories.ProductDiscountRepo;
import com.qlmh.datn_qlmh.services.DiscountPlusService;
import com.qlmh.datn_qlmh.services.DiscountService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountPlusServiceImpl implements DiscountPlusService {
    private final DiscountService discountService;
    private final ProductDiscountRepo productDiscountRepo;
    private final Mapper mapper;
    private final DiscountPlusRepo discountPlusRepo;
    @Override
    public DiscountPlusReq save(DiscountPlusReq discountPlusReq) {
        ProductDiscountEntity productDiscountEntity = productDiscountRepo.findById(discountPlusReq.getProductDiscountId()).orElseThrow(()->new NotFoundException("Product discount not found : "+discountPlusReq.getProductDiscountId()));
        DiscountReq discountReq = discountService.findById(productDiscountEntity.getProductId());
        discountPlusReq.setDiscountStart(discountReq.getDiscountStart());
        discountPlusReq.setDiscountEnd(discountReq.getDiscountEnd());
        DiscountPlusEntity discountPlusEntity  =discountPlusRepo.save(mapper.toDiscountPlusEntity(discountPlusReq));
        return mapper.toDiscountPlusReq(discountPlusEntity);
    }
    @Override
    public DiscountPlusReq update(DiscountPlusReq discountPlusReq) {
        if(discountPlusReq.getId() == null){
            throw new InvalidArgumentException("Id must not be null");
        }
        DiscountPlusEntity discountPlusEntity = discountPlusRepo.findById(discountPlusReq.getId()).orElseThrow(()->new NotFoundException("Product plus not found : "+ discountPlusReq.getId()));
        discountPlusEntity.setStatus(discountPlusReq.getStatus());
        discountPlusEntity.setDiscountAmount(discountPlusReq.getDiscountAmount());
        discountPlusEntity.setDiscountType(discountPlusReq.getDiscountType());
        return mapper.toDiscountPlusReq(discountPlusRepo.save(discountPlusEntity));
    }

    @Override
    public DiscountPlusReq findByDiscountProductId(Long id) {

        DiscountPlusEntity discountPlusEntity = discountPlusRepo.getByDiscountProductId(id);
        if(discountPlusEntity == null){
            return null;
        }
        return mapper.toDiscountPlusReq(discountPlusEntity);
    }
}
