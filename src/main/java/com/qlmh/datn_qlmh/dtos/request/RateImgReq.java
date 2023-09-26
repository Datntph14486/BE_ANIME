package com.qlmh.datn_qlmh.dtos.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateImgReq {
    private Integer id;
    private String name;
    private String url;
    private Integer rateID;
}
