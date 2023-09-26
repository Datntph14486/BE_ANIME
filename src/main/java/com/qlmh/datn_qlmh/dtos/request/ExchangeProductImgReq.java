package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.entities.RequestExchangeProductDetailEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeProductImgReq {

    private Integer id;
    private String name;
    private String url;
    private String amount;
    private Integer requestExchangeId;
}
