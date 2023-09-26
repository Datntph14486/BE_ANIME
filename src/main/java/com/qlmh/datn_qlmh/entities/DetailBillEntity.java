package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "detail_bill")
public class DetailBillEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "price_sale")
    private BigDecimal priceSale;
    @Column(name = "amount")
    private int amount;
    @Column(name = "status")
    private int status;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "bill_Id")
    private Integer billId;
    @Column(name = "type_of_bill")
    private int TypeOfBill;
    @Column(name = "deposit")
    private BigDecimal deposit;
    private BigDecimal customerMoney;

}
