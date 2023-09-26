package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.dtos.Items.BaseItems;
import com.qlmh.datn_qlmh.entities.BillEntity;
import com.qlmh.datn_qlmh.entities.DetailBillEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BillRequestv2 extends BaseItems {
    private Integer id;
    private BillEntity.StatusEnum status;
    private BigDecimal transportFee;
    @NotNull
    private BigDecimal customerMoney;
    private BigDecimal discount;
    private BillEntity.PaymentMethod paymentMethod;
    private String note;
    private String fullName;
    private String address;
    private String phoneNumber;
    private Long discountId;
    private String username;
    private BigDecimal total;
    private String voucherCode;
    private String email;
    private String estimateTime;
    private BigDecimal depositTotal;
    private String codeBill;
    private List<BillRequest.DetailBillRequest> detailBillRequests;

    @Data
    @Valid
    public static class DetailBillRequest extends BaseItems{
        private Integer id;
        private BigDecimal price;
        private BigDecimal priceSale;
        private int amount;
        private Integer productId;
        private BigDecimal deposit;
        private List<Long> discountId;
        public DetailBillRequest(DetailBillEntity detailBill){
            this.id = detailBill.getId();
            this.price = detailBill.getPrice();
            this.priceSale = detailBill.getPriceSale();
            this.productId = detailBill.getProductId();
            this.amount = detailBill.getAmount();
        }
    }

}
