package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "discount_plus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountPlusEntity extends BaseDiscountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "discount_name")
//    private String discountName;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "product_discount_id")
    private Long productDiscountId;

}
