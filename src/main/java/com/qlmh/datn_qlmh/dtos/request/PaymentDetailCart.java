package com.qlmh.datn_qlmh.dtos.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PaymentDetailCart {
    private Integer productId;
    private String productName;
    private Double subtotal;
    private Double shipping;
    private Double total;
    private Double quantity;

}
