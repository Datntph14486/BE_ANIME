package com.qlmh.datn_qlmh.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountWithBillReq extends BaseDiscount {
    private Long id;
    @NotBlank
    @JsonProperty("discountName")
    @Pattern(regexp = "[^<>{}\\/|;^:.+,~!?@#$^=&\\[\\]*]{1,120}",message = "Có lỗi xảy ra")
    private String name;
//    @NotBlank
    @Pattern(regexp = "[^<>{}\\/|;^:+,~!?@#$^=&\\[\\]*]{0,200}")
    private String description;
    @NotNull
    private Integer status;
    @Min(0)
    @NotNull
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
