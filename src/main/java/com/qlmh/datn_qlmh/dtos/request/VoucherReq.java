package com.qlmh.datn_qlmh.dtos.request;

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
public class VoucherReq extends BaseDiscount {
    private Long id;
    @NotBlank
    @Pattern(regexp = "[^<>{}\\/|;^:.+,~!?@#$^=&\\[\\]*]{1,120}",message = "Có lỗi xảy ra")
    private String name;
//    @NotBlank
    @Pattern(regexp = "[^<>{}\\/|;^:.+,~!?@#$^=&\\[\\]*]{0,200}",message = "Có lỗi xảy ra")
    private String description;
    @NotNull
    private Integer status;
    @NotNull
    @Min(0)
    private BigDecimal minPrice;
//    @NotNull
//    @Min(0)
    private BigDecimal maxPrice;
}
