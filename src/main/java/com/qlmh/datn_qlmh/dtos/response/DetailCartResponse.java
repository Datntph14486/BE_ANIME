package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.dtos.Items.BaseItems;
import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailCartResponse extends BaseItems implements Serializable {
    private Integer id;
    private Integer cartId;
    private int amount;
    private Integer productId;
    private BigDecimal priceSale;
    private List<ProductDiscountEntity> productDiscounts;
    private int typeOder;

}
