package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.common.PageData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Deprecated
public class DiscountProductDto {
    private DiscountReq discountReq;
    private PageData<ProductForDiscountDto> productForDiscountDto;
}
