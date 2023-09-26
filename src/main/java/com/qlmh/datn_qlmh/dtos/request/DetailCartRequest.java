package com.qlmh.datn_qlmh.dtos.request;

import lombok.Data;
@Data
public class DetailCartRequest{
    private int status;
    private int amount;
    private Integer productId;
    private String description;
}
