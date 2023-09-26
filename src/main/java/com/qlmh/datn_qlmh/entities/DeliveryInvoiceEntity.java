package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "delivery_invoice")
public class DeliveryInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    private String deliveryId;
    @Column
    private Integer billId;
}