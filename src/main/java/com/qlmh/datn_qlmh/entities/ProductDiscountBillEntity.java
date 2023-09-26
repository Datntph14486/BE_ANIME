package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "product_discount_bill")
@Getter
@Setter

public class ProductDiscountBillEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_discount_id")
    private Long productDiscountId;
    @Column(name = "bill_detail_id")
    private Integer billDetailId;
}
