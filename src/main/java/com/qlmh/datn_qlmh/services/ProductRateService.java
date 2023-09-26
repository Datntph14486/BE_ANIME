package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.ProductRateReq;
import com.qlmh.datn_qlmh.entities.ProductRateEntity;

public interface ProductRateService {
    public ProductRateReq findProdcutID(Integer productID);
    public ProductRateReq create(ProductRateReq productRateReq);
    public ProductRateReq update(ProductRateReq productRateReq);
    public double getStartOfProduct(Integer productID);

    ProductRateEntity getByProductId(Integer productID);


}
