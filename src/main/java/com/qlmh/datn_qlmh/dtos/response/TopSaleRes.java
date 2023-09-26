package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TopSaleRes {
    private Integer productId;
    private String nameFigure;
    private Long quantity;
    private BigDecimal priceSale;
    private BigDecimal totalPriSale;
}
