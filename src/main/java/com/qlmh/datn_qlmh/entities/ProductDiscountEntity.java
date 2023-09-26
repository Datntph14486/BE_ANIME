package com.qlmh.datn_qlmh.entities;

import com.qlmh.datn_qlmh.dtos.request.ProductDiscountDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = "product_discount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDiscountEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status")
    private Boolean status;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "discount_id")
    private Long discountId;
    public ProductDiscountEntity(ProductDiscountDto productDiscountDto){
        this.id = productDiscountDto.getId();
        this.status = productDiscountDto.getStatus();
        this.productId = (long)productDiscountDto.getProduct().getId();
        this.discountId = productDiscountDto.getDiscountId();
    }
}
