package com.qlmh.datn_qlmh.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailBillRes {
    private Integer id;
    private String nameProduct;
    private Integer productId;
    private String categoty;
    private String urlProduct;
    private String createdBy;
    private Boolean isRate;
}
