package com.qlmh.datn_qlmh.dtos.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportRateAdminRes {
    private RateRes rate;
    private Integer toltalreport;
    private List<ReportRateDetailRes> listUserreport;
    private String productName;
}
