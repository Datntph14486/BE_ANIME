package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_rate")
public class ProductRateEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="product_id")
    private Integer productID;
    @Column(name ="rate_count")
    private Integer rateCount;
    @Column(name ="start_count")
    private Integer startCount;


}
