package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StatisInfoDetailRes {
    private String figure;
    private Date createDate;
    private Date updateDate;
    private String createBy;
    private Integer amount;
    private BigDecimal priceSale;
    private BigDecimal priceMo;
}
