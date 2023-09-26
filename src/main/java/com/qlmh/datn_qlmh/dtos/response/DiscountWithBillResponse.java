package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.entities.BaseDiscountEntity;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class DiscountWithBillResponse extends BaseDiscountEntity {
    private Integer id;
    private String discountName;
    private String description;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer status;
    private BigDecimal priceDiscount;
}
