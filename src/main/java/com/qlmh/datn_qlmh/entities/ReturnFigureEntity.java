package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "return_figure_entity")
public class ReturnFigureEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_product")
    private String nameProduct;
    @Column(name = "wrong_product")
    private Boolean wrongProduct;
    @Column(name = "lack_of_accessories")
    private Boolean lackOfAccessories;
    @Column(name = "fake")
    private Boolean fake;
    @Column(name = "second_hand")
    private Boolean secondHand;
    @Column(name = "status")
    private Integer status;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "total_refund")
    private BigDecimal totalRefund;
    @Column(name = "request_id")
    private Integer requestId;
}
