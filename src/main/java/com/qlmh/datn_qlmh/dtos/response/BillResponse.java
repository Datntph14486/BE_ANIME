package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.dtos.Items.BaseItems;
import com.qlmh.datn_qlmh.entities.BillEntity;
import com.qlmh.datn_qlmh.entities.DiscountEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class BillResponse extends BaseItems{
    private Integer id;
    private BillEntity.StatusEnum status;
    private String statusName;
    private String codeBill;
    @NotNull(message = "Phí vận chuyển không để trống")
    private BigDecimal transportFee;
    @NotNull
    private BigDecimal customerMoney;
    private BigDecimal discount;
    @NotNull(message = "Phương thức thanh toán không để trống")
    private BillEntity.PaymentMethod paymentMethod;
    private String note;
    private String fullName;
    @NotNull(message = "Địa chỉ không để trống")
    private String address;
    @NotNull(message = "Số điện thoại không được để trống")
    private String phoneNumber;
    private Long discountId;
    @NotNull(message = "Người dùng không để trống")
    private String username;
    @NotNull(message = "Tổng tiền không để trống")
    private BigDecimal total;
    private String voucherCode;
    private String email;
    private String addressCode;
    private BigDecimal refund;
    private int amount;
    private List<BillResponse.DetailBillResponse> detailBillResponses;

    @Data
    @Valid
    public static class DetailBillResponse extends BaseItems {
        private Integer id;
        private BigDecimal price;
        private BigDecimal priceSale;
        private int amount;
        private Integer productId;
        private String productName;
        private BigDecimal deposit;
        private String categoryName;
        private String url;
        private List<DiscountEntity> discountId;
        private int TypeOfBill;
        private BigDecimal customerMoney;
        private float weight;
        private float height;
        private boolean isRate;
        private int amountProduct;

    }

}
