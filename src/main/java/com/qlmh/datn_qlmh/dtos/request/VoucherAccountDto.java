package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.common.PageData;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherAccountDto {
    private VoucherReq voucherReq;
    private PageData<VoucherAccountReq> voucherAccountReq;
}
