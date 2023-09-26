package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.entities.VoucherAccountEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VoucherAccountReq {
    private Long id;
    private String code;
//    @NotNull
    private Boolean active;
    @NotNull
    private Long voucherId;
    @NotNull
    private String username;

    public VoucherAccountReq(VoucherAccountEntity voucherAccountEntity){
        this.id = voucherAccountEntity.getId();
        this.active = voucherAccountEntity.getStatus();
        this.voucherId = voucherAccountEntity.getVoucherId();
        this.username = voucherAccountEntity.getUsername();
        this.code = voucherAccountEntity.getCode();
    }

}
