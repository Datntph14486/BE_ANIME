package com.qlmh.datn_qlmh.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlackListReq {
    private Long id;
    @NotNull
    private Long discountId;
    @NotNull
    private Long productId;
    @NotNull
    private Boolean status;

}
