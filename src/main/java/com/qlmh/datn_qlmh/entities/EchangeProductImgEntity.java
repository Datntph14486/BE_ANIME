package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="echange_product_img")
public class EchangeProductImgEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "amount")
    private String amount;
    @ManyToOne
    @JoinColumn(name = "request_exchange_id")
    RequestExchangeProductDetailEntity requestExchangeProductDetailEntity;
}
