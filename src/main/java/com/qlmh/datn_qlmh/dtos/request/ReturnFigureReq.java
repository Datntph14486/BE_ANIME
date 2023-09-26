
package com.qlmh.datn_qlmh.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnFigureReq {
    private Integer id;
    private String nameProduct;
    private Boolean wrongProduct;
    private Boolean lackOfAccessories;
    private Boolean fake;
    private Boolean secondHand;
    private Integer quantity;
    private BigDecimal totalRefund;
    private Integer requestId;
}

