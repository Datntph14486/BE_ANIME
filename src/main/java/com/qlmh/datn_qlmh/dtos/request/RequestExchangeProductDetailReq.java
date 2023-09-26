package com.qlmh.datn_qlmh.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestExchangeProductDetailReq {
    private Integer id;
    private String wrongProduct;
    private String lackOfAccessories;
    private String productError;
    private String fake;
    private Integer detailBillId;
    private Integer requestExchangeId;


}
