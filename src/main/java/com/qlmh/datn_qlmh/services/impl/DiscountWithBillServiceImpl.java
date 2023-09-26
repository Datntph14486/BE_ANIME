package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import com.qlmh.datn_qlmh.dtos.request.DiscountWithBillReq;
import com.qlmh.datn_qlmh.entities.DiscountEntity;
import com.qlmh.datn_qlmh.entities.DiscountWithBillEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.DiscountRepo;
import com.qlmh.datn_qlmh.repositories.DiscountWithBillRepo;
import com.qlmh.datn_qlmh.services.DiscountWithBillService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountWithBillServiceImpl implements DiscountWithBillService {
    private final DiscountWithBillRepo discountWithBillRepo;
    @Autowired
    private DiscountRepo discountService;

    private final Mapper mapper;


    @Autowired
    public DiscountWithBillServiceImpl(DiscountWithBillRepo discountWithBillRepo, Mapper map) {
        this.discountWithBillRepo = discountWithBillRepo;
        mapper = map;
    }

    @Override
    public DiscountWithBillReq save(DiscountWithBillReq voucherReq) {
        DiscountWithBillEntity discountWithBillEntity = mapper.toDiscountWithBillEntity(voucherReq);
        discountWithBillEntity = discountWithBillRepo.save(discountWithBillEntity);
        return mapper.toDiscountWithBillReq(discountWithBillEntity);
    }

    @Override
    public void delete(Long id) {

        DiscountWithBillEntity discountWithBillEntity = discountWithBillRepo.findById(id).orElseThrow(()->new NotFoundException("Discount not found : "+id));
        discountWithBillEntity.setStatus(0);
        discountWithBillRepo.save(discountWithBillEntity);
    }

    @Override
    public DiscountWithBillReq update(DiscountWithBillReq discountWithBillReq) {
        if(discountWithBillReq.getId() == null) throw new InvalidArgumentException("id is not null ");

        DiscountWithBillEntity discountWithBillEntity = discountWithBillRepo.findById(discountWithBillReq.getId()).orElseThrow(()->new NotFoundException("Discount not found : "+discountWithBillReq.getId()));
        discountWithBillEntity = mapper.toDiscountWithBillEntity(discountWithBillReq);
        discountWithBillEntity =  discountWithBillRepo.save(discountWithBillEntity);
        return mapper.toDiscountWithBillReq(discountWithBillEntity);
    }

    @Override
    public PageData<DiscountWithBillReq> findAll(Pageable pageable) {
        return new PageData<DiscountWithBillReq>(discountWithBillRepo.findAll(pageable));
    }
    @Override
    public PageData<DiscountWithBillReq> findAll(Pageable pageable, String name, Integer status, String from, String to) {
        //          status : null -- all || 1--true || 0 --false
        Specification<DiscountWithBillEntity> receptionistSpecification = (root, cq, cb) -> {
            System.out.println("apply status for discount : "+from + " --- "+to);
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, name, "discountName"),
                    QueryUtils.buildGreaterThanFilter(root, cb, "discountStart", from, Constant.DATE_FORMAT_2),
                    QueryUtils.buildLessThanFilter(root, cb, "discountEnd", to, Constant.DATE_FORMAT_2),
                    QueryUtils.buildEqFilter(root, cb, "status", status));
        };


        Page<DiscountWithBillEntity> page = discountWithBillRepo.findAll(receptionistSpecification,pageable);
        return PageData.of(page,page.toList().stream().map((item)->mapper.toDiscountWithBillReq(item)).collect(Collectors.toList()));
    }

    @Override
    public DiscountWithBillReq findById(Long id) {

        DiscountWithBillEntity discountWithBillEntity = discountWithBillRepo.findById(id).orElseThrow(()->new NotFoundException("Discount not found : "+id));
        return mapper.toDiscountWithBillReq(discountWithBillEntity);
    }

    @Override
    public List<DiscountWithBillReq> get() {
        List<DiscountWithBillEntity> discountEntities = discountWithBillRepo.getDiscountActive(Sort.by(Sort.Direction.DESC,"discountStart"),1,new Date());
        return discountEntities.stream().map((item)->mapper.toDiscountWithBillReq(item)).collect(Collectors.toList());
    }
}
