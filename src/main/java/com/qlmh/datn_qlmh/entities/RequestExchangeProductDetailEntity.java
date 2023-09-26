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
@Table(name="request_exchange_product_detail")
public class RequestExchangeProductDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "wrong_product")
    private String wrongProduct;
    @Column(name = "lack_of_accessories")
    private String lackOfAccessories;
    @Column(name = "product_error")
    private String productError;
    @Column(name = "fake")
    private String fake;
    @Column(name="detail_bill_id")
    private Integer detailBillId;
    @ManyToOne
    @JoinColumn(name = "request_exchange_id")
    RequestExchangeProductEntity exchangeProductEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "requestExchangeProductDetailEntity")
    List<EchangeProductImgEntity> echangeProductImgEntities;

}
