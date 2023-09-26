package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.entities.VoucherAccountEntity;
import com.qlmh.datn_qlmh.entities.VoucherEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AccountVoucherDto {
    private String username;
    private String voucherCode;
    private String nameVoucher;
    private Date from;
    private Date to;
    protected Byte discountType;
    protected BigDecimal discountAmount;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean status;
    public AccountVoucherDto(VoucherEntity voucher, VoucherAccountEntity voucherAccount){
        this.username = voucherAccount.getUsername();
        this.voucherCode = voucherAccount.getCode();
        this.nameVoucher = voucher.getVoucherName();
        this.from = voucher.getDiscountStart();
        this.to = voucher.getDiscountEnd();
        this.discountType = voucher.getDiscountType();
        this.discountAmount = voucher.getDiscountAmount();
        this.minPrice = voucher.getMinPrice();
        this.maxPrice = voucher.getMaxPrice();
        this.status = voucherAccount.getStatus();
    }

}
