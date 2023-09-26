package com.qlmh.datn_qlmh.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountReq extends BaseDiscount {
    private Long id;
    @NotBlank
    @Pattern(regexp = "[^<>{}\\/|;^:.+,~!?@#$^=&\\[\\]*]{1,120}",message = "Có lỗi xảy ra")
    private String name;
//    @NotBlank
    @Pattern(regexp = "[^<>{}\\/|;^:.+,~!?@#$^=&\\[\\]*]{0,200}",message = "Có lỗi xảy ra")
    private String description;
    @NotNull
    private Integer status;
    private Long categoryId;

}
