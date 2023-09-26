package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.dtos.request.AccountVoucherDto;
import com.qlmh.datn_qlmh.dtos.request.VoucherReq;
import com.qlmh.datn_qlmh.entities.VoucherEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.VoucherRepo;
import com.qlmh.datn_qlmh.security.JwtService;
import com.qlmh.datn_qlmh.services.VoucherService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import com.qlmh.datn_qlmh.utilities.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepo voucherRepo;
    private final Mapper voucherMapper;
    @Autowired
    JwtService jwtService;


    @Autowired
    public VoucherServiceImpl(VoucherRepo voucherRepo, Mapper voucherMapper) {
        this.voucherRepo = voucherRepo;
        this.voucherMapper = voucherMapper;
    }

    @Override
    public VoucherReq save(VoucherReq voucherReq) {
        VoucherEntity voucherEntity = voucherMapper.toVoucherEntity(voucherReq);
        System.out.println(voucherEntity);
        System.out.println(voucherEntity.getDiscountAmount() +" - "+ voucherEntity.getDiscountStart() +"");
        voucherEntity = voucherRepo.save(voucherEntity);
        System.out.println(voucherEntity);
        return voucherMapper.toVoucherReq(voucherEntity);
    }

    @Override
    public void delete(Long id) {

        VoucherEntity voucherEntity = voucherRepo.findById(id).orElseThrow(()->new NotFoundException("Voucher not found : "+id));
        voucherEntity.setStatus(0);
        voucherRepo.save(voucherEntity);
    }
    @Override
    public VoucherReq update(VoucherReq voucherReq) {
        if(voucherReq.getId() == null) throw new InvalidArgumentException("id is not null ");

        VoucherEntity voucherEntity = voucherRepo.findById(voucherReq.getId()).orElseThrow(()->new NotFoundException("Voucher not found : "+voucherReq.getId()));
        voucherEntity = voucherMapper.toVoucherEntity(voucherReq);
        voucherEntity =  voucherRepo.save(voucherEntity);
        return voucherMapper.toVoucherReq(voucherEntity);
    }

    @Override
    public PageData<VoucherReq> findAll(Pageable pageable, String name, Integer status, String from, String to) {
        Specification<VoucherEntity> specification = (root, cq, cb) -> {
            System.out.println("apply status for discount : "+from + " --- "+to);
            return cb.and(
                    QueryUtils.buildLikeFilter(root, cb, name, "voucherName"),
                    QueryUtils.buildGreaterThanFilter(root, cb, "discountStart", from, Constant.DATE_FORMAT_2),
                    QueryUtils.buildLessThanFilter(root, cb, "discountEnd", to, Constant.DATE_FORMAT_2),
                    QueryUtils.buildEqFilter(root, cb, "status", status));
        };
        Page<VoucherEntity> page = voucherRepo.findAll(specification,pageable);
        return PageData.of(page,page.toList().stream().map((item)->voucherMapper.toVoucherReq(item)).collect(Collectors.toList()));
    }

    @Override
    public VoucherReq findById(Long id) {

        VoucherEntity voucherEntity = voucherRepo.findById(id).orElseThrow(()->new NotFoundException("Voucher not found : "+id));
        return voucherMapper.toVoucherReq(voucherEntity);
    }

    @Override
    public List<AccountVoucherDto> get(String token) {
        String username = jwtService.getUsernameFromJwtToken(token,false);
        System.out.println("user : "+username);
        return voucherRepo.findByUsername(username);
    }
}
