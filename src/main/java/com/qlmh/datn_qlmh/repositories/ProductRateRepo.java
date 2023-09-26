package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.ProductRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRateRepo extends JpaRepository<ProductRateEntity, Integer> {
    @Query("SELECT P FROM ProductRateEntity  P WHERE P.productID=?1")
    ProductRateEntity getByProductId(Integer productID);
}
