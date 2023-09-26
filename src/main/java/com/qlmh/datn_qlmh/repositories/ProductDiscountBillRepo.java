package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.ProductDiscountBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDiscountBillRepo extends JpaRepository<ProductDiscountBillEntity,Long> {
    List<ProductDiscountBillEntity> findByBillDetailId(Integer idBillDetail);
}
