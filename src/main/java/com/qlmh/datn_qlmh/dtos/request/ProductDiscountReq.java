package com.qlmh.datn_qlmh.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDiscountReq {
    private Long id;
    @NotNull
    private Long discountId;
    @NotNull
    private Long productId;
    @NotNull
    private Boolean status;
}
