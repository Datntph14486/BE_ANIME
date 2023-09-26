package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.entities.RequestExchangeProductEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExchangeProductReq {
    private Integer id;
    private String productName;
    private String quantity;
    private String amount;
    private Integer requestExchangeId;
}
