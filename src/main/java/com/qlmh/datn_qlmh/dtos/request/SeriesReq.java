package com.qlmh.datn_qlmh.dtos.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesReq {
    private Integer id;
    private String name;
    private Date createdDate = new Date();
    private  String CreatedBy;
}
