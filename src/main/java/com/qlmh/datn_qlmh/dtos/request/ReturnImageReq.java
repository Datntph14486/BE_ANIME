package com.qlmh.datn_qlmh.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnImageReq {
    private Integer id;
    private String url;
    private Integer requestId;
}
