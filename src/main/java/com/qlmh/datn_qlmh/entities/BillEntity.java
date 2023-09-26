package com.qlmh.datn_qlmh.entities;

import com.qlmh.datn_qlmh.dtos.request.BillRequestv2;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "bill")
public class BillEntity extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Column(name = "code_bill")
    private String codeBill;
    @Column(name = "transport_fee")
    private BigDecimal transportFee;
    @Column(name = "customer_money")
    private BigDecimal customerMoney;
    @Column
    private BigDecimal discount;
    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Column(name = "note")
    private String note;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "type_of_bill")
    private int TypeOfBill;
    @Column(name = "voucher_id")
    private Long voucherId;
    @Column(name = "discount_id")
    private Long discountId;
    @Column(name = "voucher_code")
    private String voucherCode;
    @Column(name = "username")
    private String username;
    @Column
    private BigDecimal total;
    @Column(name = "address_code")
    private String addressCode;
    @Column(name = "refund")
    private BigDecimal refund;
    @Column(name = "amount")
    private int amount;
    @Column(name = "is_online")
    private Boolean isOnline = true;
    @AllArgsConstructor
    @Getter
    public enum PaymentMethod {
        MONEY,
        CARD,
        CARD_MONEY
    }

    @AllArgsConstructor
    @Getter
    public enum StatusEnum {
        HOA_DON_CHO,
        CHO_XAC_NHAN,
        XAC_NHAN,
        DA_TAO_DON,
        DANG_VAN_CHUYEN,
        DA_VE_KHO,
        DANG_GIAO,
        DA_GIAO,
        HUY,
        TRA_HANG,
        HOA_DON_CHO_TAI_CUA_HANG,
        HUY_GHN
    }
    public BillEntity(BillRequestv2 bill){
        this.username = bill.getUsername();
        this.address = bill.getAddress();
        this.phoneNumber = bill.getPhoneNumber();
        this.email = bill.getEmail();
        this.fullName = bill.getFullName();
    }
}
