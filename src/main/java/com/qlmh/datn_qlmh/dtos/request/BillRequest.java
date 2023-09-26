package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.dtos.Items.BaseItems;
import com.qlmh.datn_qlmh.entities.BillEntity;
import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class BillRequest extends BaseItems {
    private Integer id;
    private BillEntity.StatusEnum status;
    @NotNull(message = "Phí vận chuyển không để trống")
    private BigDecimal transportFee;
    @NotNull
    private BigDecimal customerMoney;
    private BigDecimal discount;
    @NotNull(message = "Phương thức thanh toán không để trống")
    private BillEntity.PaymentMethod paymentMethod;
    private String note;
    private String fullName;
    @NotBlank(message = "Địa chỉ không để trống")
    @NotNull(message = "Địa chỉ không để trống")
    private String address;
    @NotNull(message = "Số điện thoại không được để trống")
    private String phoneNumber;
    private Long discountId;
    private String username;
    @NotNull(message = "Tổng tiền không để trống")
    private BigDecimal total;
    private String voucherCode;
    private String email;
    private String addressCode;
    private BigDecimal refund;
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
        private BigDecimal refund;
        private List<ProductDiscountEntity> discountId;

    }

}
