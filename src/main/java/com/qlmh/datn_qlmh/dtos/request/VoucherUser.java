package com.qlmh.datn_qlmh.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherUser extends BaseDiscount{
    private String name;
    private String code;
}
