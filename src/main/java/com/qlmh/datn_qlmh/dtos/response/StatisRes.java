package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisRes {
    private BigDecimal totalPrice,totalPriceSale;
    private Integer month;
    private BigDecimal priceExpense;
    private Long quantity;
}
