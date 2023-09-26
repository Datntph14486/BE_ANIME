package com.qlmh.datn_qlmh.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesReq {
    private Integer id;
    private String name;
    private Date createDate = new Date();
    private Boolean isActive = false;
    private String createBy;
    private String updateBy;
}
