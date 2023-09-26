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
public class DiscountPlusReq extends BaseDiscount{
    private Long id;
    private String description;
    private Boolean status;
    @NotNull
    private Long productDiscountId;
}
