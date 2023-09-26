package com.qlmh.datn_qlmh.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequestFigureReq extends BaseReturn{

    private Integer id;
    private String description;
    private Integer status;
    private String userName;

}
