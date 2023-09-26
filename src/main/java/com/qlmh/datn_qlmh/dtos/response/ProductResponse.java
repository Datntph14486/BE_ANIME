package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.entities.ImageEntity;
import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponse {
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
    private String categoryName;
    private String manufacturerName;
    private String seriesName;
    private String availableName;
    private BigDecimal price;
    private String status;
    private List<ImageEntity> listImage ;
    private List<ProductDiscountEntity> productDiscounts;
    private BigDecimal discountAmount;
    private BigDecimal priceSales;
    private BigDecimal priceNew;
}
