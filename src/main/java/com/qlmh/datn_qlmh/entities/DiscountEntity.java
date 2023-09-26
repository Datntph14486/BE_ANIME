package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "discount")
@Getter @Setter
@ToString
public class DiscountEntity extends BaseDiscountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "discount_name")
    private String discountName;
    @Column(name = "description")
    private String description;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "status")
    private Integer status;

}
