package com.qlmh.datn_qlmh.dtos.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRateReq {
    private Integer id;
    private Integer rateCount;
    private Integer productID;
    private Integer startCount;
}
