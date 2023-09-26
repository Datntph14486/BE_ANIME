package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisByDayRes {
    private Date dateColum;
    private BigDecimal sumPriceSales;
    private BigDecimal sumPriceProfit;
    private BigDecimal sumPriceCost;
    private Long amount;

}
