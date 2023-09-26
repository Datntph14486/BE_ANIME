package com.qlmh.datn_qlmh.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Product")
@ToString
public class ProductEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "figure")
    private String figure;
    @Column(name = "description")
    private String description;
    @Column(name = "ratio")
    private String ratio;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "material")
    private String material;
    @Column(name = "title")
    private int title;
    @Column(name = "height")
    private float height;
    @Column(name = "weight")
    private float weight;
    @Column(name = "recommended_age")
    private String recommendedAge;
    @Column(name = "accessories_included")
    private String accessoriesIncluded;
    @Column(name = "gender")
    private boolean gender;
    @Column(name = "manufacturer_id")
    private Integer manufacturerID;
    @Column(name = "series_id")
    private Integer seriesID;
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "status")
    private int status;
    @Column (name = "price_sales")
    private BigDecimal priceSales;
    @Column(name = "price_new")
    private BigDecimal priceNew;
}
