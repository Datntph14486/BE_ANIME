package com.qlmh.datn_qlmh.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportPdfDTO {
    private String name;
    private Integer quantity;
    private Float wight;
    private BigDecimal price;
    private BigDecimal total;
}
