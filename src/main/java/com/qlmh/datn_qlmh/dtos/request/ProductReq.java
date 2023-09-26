package com.qlmh.datn_qlmh.dtos.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReq {
    private Integer id;
    private String figure;
    private Boolean available;
    private String description;
    private Integer rate;
    private String ratio;
    private Integer quantity;
    private String material;
    private int title;
    private String height;
    private String weight;
    private String recommendedAge;
    private String accessoriesIncluded;
    private boolean gender;
    private String manufacturerID;
    private Integer seriesID;
    private Integer categoryId;
    private BigDecimal price;
    private int status;
    private BigDecimal priceNew;
}
