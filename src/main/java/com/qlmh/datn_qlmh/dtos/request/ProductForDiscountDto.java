package com.qlmh.datn_qlmh.dtos.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.qlmh.datn_qlmh.entities.ProductEntity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@JsonRootName("Product")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductForDiscountDto {
    private Integer id;
    private String figure;
    private String description;
    private CategoriesReq categoryId;
    private ManufacturerReq manufacturerID;
    private SeriesReq seriesID;
    private BigDecimal price;

    private BigDecimal priceSale;
    private int status;
    
    public ProductForDiscountDto(ProductEntity productEntity){
        this.id=productEntity.getId();
        this.figure=productEntity.getFigure();
        this.description=productEntity.getDescription();
        this.price=productEntity.getPriceSales();
        this.status = productEntity.getStatus();
    }

}