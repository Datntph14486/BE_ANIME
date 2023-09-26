package com.qlmh.datn_qlmh.dtos.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class DetailBillRequest {
    private Integer id;
    private BigDecimal price;
    private BigDecimal priceSale;
    private int amount;
    private Integer productId;
    private BigDecimal deposit;
    private List<Long> discountId;
    private String username;
}
