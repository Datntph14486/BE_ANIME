package com.qlmh.datn_qlmh.dtos.response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeriesResp {
    private Integer id;
    private String name;
    private Date createdDate = new Date();
    private String CreatedBy;
    private String UpdatedBy;
}
