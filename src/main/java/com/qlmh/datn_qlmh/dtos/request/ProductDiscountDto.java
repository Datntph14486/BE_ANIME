package com.qlmh.datn_qlmh.dtos.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDiscountDto {
    private Long id;
    private Boolean status;
    private ProductForDiscountDto product;
    private Long discountId;
    private DiscountPlusReq discountPlus;

    public ProductDiscountDto(ProductDiscountEntity productDiscountEntity){
        this.id = productDiscountEntity.getId();
        this.status = productDiscountEntity.getStatus();
        this.discountId = productDiscountEntity.getDiscountId();
    }
}
