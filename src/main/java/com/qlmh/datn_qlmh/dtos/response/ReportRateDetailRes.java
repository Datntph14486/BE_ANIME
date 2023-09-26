package com.qlmh.datn_qlmh.dtos.response;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReportRateDetailRes {
    private Integer id;
    private String create_by;
    private Integer reportRateId;
}
