package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.entities.DetailBillEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DetailBillDto {
    private Integer id;
    private BigDecimal price;
    private BigDecimal priceSale;
    @NotNull
    private int amount;
    private Integer productId;
    private BigDecimal deposit;
    private List<Long> discountId;
    private Integer billId;
    public DetailBillDto(DetailBillEntity detailBill){
        this.id = detailBill.getId();
        this.price = detailBill.getPrice();
        this.priceSale = detailBill.getPriceSale();
        this.productId = detailBill.getProductId();
        this.amount = detailBill.getAmount();
        this.billId = detailBill.getBillId();
    }
}
