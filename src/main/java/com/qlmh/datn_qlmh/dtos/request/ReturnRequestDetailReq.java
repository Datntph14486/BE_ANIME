package com.qlmh.datn_qlmh.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequestDetailReq {
    private Integer id;
    private Integer requestId;
    private Integer detailBillId;
    private Integer quantity;
}
