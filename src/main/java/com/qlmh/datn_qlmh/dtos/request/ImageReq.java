package com.qlmh.datn_qlmh.dtos.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageReq implements Serializable {
    private Integer id;
    private String url;
    private Integer productID;
}
