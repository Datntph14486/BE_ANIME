package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "pre_oder")
public class PreOderEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "deposit")
    private BigDecimal deposit;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PreOderEntity.StatusEnum status;
    @Column(name = "transport_fee")
    private BigDecimal transportFee;
    @Column(name = "estimated_time")
    private String estimateTime;
    @Column
    @Enumerated(EnumType.STRING)
    private PreOderEntity.PaymentMethod paymentMethod;
    @Column(name = "note")
    private String note;
    @Column(name = "full_name")
    private String fullName;
    @Column
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "username")
    private String username;
    @Column
    private BigDecimal total;

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
        DANG_VAN_CHUYEN,
        DA_VE_KHO,
        DANG_GIAO,
        DA_GIAO,
        HUY,
        TRA_HANG
    }
}
