package com.qlmh.datn_qlmh.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="request_exchange_product")
public class RequestExchangeProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String Description;
    @Column(name = "status")
    private String Status;
    @Column(name = "user_name")
    private String userName;
    @JsonIgnore
    @OneToMany(mappedBy = "exchangeProductEntity")
    List<ExchangeProductEntity> exchangeProductEntities;
    @JsonIgnore
    @OneToMany(mappedBy = "exchangeProductEntity")
    List<RequestExchangeProductDetailEntity> requestExchangeProductDetailEntities;

}
