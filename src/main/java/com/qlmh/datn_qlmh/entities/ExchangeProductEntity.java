package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="exchange_product")
public class ExchangeProductEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "quantity")
    private String quantity;
    @Column(name = "amount")
    private String amount;
    @ManyToOne
    @JoinColumn(name = "request_exchange_id")
    RequestExchangeProductEntity exchangeProductEntity;
}
