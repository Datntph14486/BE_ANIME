package com.qlmh.datn_qlmh.dtos.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportRateReq {
    private Integer id;
    private Integer totalReport;
    private Integer rateID;
}
