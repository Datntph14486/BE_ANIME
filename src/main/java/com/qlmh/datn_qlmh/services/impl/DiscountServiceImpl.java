package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import com.qlmh.datn_qlmh.entities.DiscountEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.DiscountRepo;
import com.qlmh.datn_qlmh.services.DiscountService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepo discountRepo;
    private final Mapper mapper;

    @Autowired
    public DiscountServiceImpl(DiscountRepo discountRepo, Mapper map) {
        this.discountRepo =discountRepo;
        mapper = map;
    }

    @Override
    public DiscountReq save(DiscountReq discountReq) {
        DiscountEntity discountEntity = mapper.toDiscountEntity(discountReq);
        discountEntity =discountRepo.save(discountEntity);
        return mapper.toDiscountReq(discountEntity);
    }

    @Override
    public void delete(Long id) {

        DiscountEntity discountEntity =discountRepo.findById(id).orElseThrow(()->new NotFoundException("Discount not found : "+id));
        discountEntity.setStatus(0);
        discountRepo.save(discountEntity);
    }

    @Override
    public DiscountReq update(DiscountReq discountReq) {
        if(discountReq.getId() == null) throw new InvalidArgumentException("id is not null ");

        DiscountEntity discountEntity =discountRepo.findById(discountReq.getId()).orElseThrow(()->new NotFoundException("Discount not found : "+discountReq.getId()));
        discountEntity = mapper.toDiscountEntity(discountReq);
        discountEntity = discountRepo.save(discountEntity);
        return mapper.toDiscountReq(discountEntity);
    }

    @Override
    public PageData<DiscountReq> findAll(Pageable pageable, String name, Integer status, String from, String to){
        Specification<DiscountEntity> receptionistSpecification = (root, cq, cb) -> {
            System.out.println("apply status for discount : "+from + " --- "+to);
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, name, "discountName"),
                    QueryUtils.buildGreaterThanFilter(root, cb, "discountStart", from, Constant.DATE_FORMAT_2),
                    QueryUtils.buildLessThanFilter(root, cb, "discountEnd", to, Constant.DATE_FORMAT_2),
                    QueryUtils.buildEqFilter(root, cb, "status", status));
        };


        Page<DiscountEntity> page = discountRepo.findAll(receptionistSpecification,pageable);
        return PageData.of(page,page.toList().stream().map((item)->mapper.toDiscountReq(item)).collect(Collectors.toList()));
    }

    @Override
    public DiscountReq findById(Long id) {

        DiscountEntity discountEntity =discountRepo.findById(id).orElseThrow(()->new NotFoundException("Discount not found : "+id));
//        blackListService.getBlackListByDiscountId(discountEntity.getId());
        return mapper.toDiscountReq(discountEntity);
    }
}
