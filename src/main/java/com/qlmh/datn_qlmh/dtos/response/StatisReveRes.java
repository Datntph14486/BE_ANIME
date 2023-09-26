package com.qlmh.datn_qlmh.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisReveRes {
    private BigDecimal doanhThu;
    private BigDecimal giaVon;
    private BigDecimal loiNhuan;
    private Integer thang;
    private Long slDaBan;
}
