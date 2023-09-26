package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisDetailRes {
    private Integer id;
    private String nameFigure;
    private Date createDate;
    private String createBy;
    private Date updateDate;
    private String updateBy;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal priceSale;
    private String urlImage;
}
