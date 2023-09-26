package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.ProductRateReq;
import com.qlmh.datn_qlmh.entities.ProductRateEntity;
import com.qlmh.datn_qlmh.entities.VoucherEntity;
import com.qlmh.datn_qlmh.exceptions.InvalidArgumentException;
import com.qlmh.datn_qlmh.exceptions.NotFoundException;
import com.qlmh.datn_qlmh.repositories.ProductRateRepo;
import com.qlmh.datn_qlmh.services.ProductRateService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRateServiceImpl implements ProductRateService {
    @Autowired
    ProductRateRepo productRateRepo;

    @Autowired
    Mapper mapper;

    @Override
    public ProductRateReq findProdcutID(Integer productID) {
        ProductRateEntity rateEntity = productRateRepo.getByProductId(productID);
        return mapper.toProductRateReq(rateEntity);
    }

    @Override
    public ProductRateReq create(ProductRateReq productRateReq) {
        ProductRateEntity rateEntity = mapper.toProductRateEntity(productRateReq);
        productRateRepo.save(rateEntity);
        return mapper.toProductRateReq(rateEntity);
    }

    @Override
    public ProductRateReq update(ProductRateReq productRateReq) {
        if (productRateReq.getId() == null) throw new InvalidArgumentException("id is not null ");
        ProductRateEntity productRateEntity = productRateRepo.findById(productRateReq.getId()).orElseThrow(() -> new NotFoundException("Product Rate not found : " + productRateReq.getId()));
        productRateEntity = mapper.toProductRateEntity(productRateReq);
        productRateRepo.save(productRateEntity);
        return mapper.toProductRateReq(productRateEntity);
    }

    @Override
    public double getStartOfProduct(Integer productID) {
        ProductRateEntity rateEntity = productRateRepo.getByProductId(productID);
        if(rateEntity!=null){
            double start = (float) rateEntity.getStartCount() / rateEntity.getRateCount();
            System.out.println(start);
            return (double) Math.round(start * 10) / 10;
        }else {
            return 0;
        }

    }

    @Override
    public ProductRateEntity getByProductId(Integer productID) {
        ProductRateEntity rateEntity = productRateRepo.getByProductId(productID);
        if(rateEntity == null){
            return null;
        }else
        {
            return rateEntity;
        }

    }
}
