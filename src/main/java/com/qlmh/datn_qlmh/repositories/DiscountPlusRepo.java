package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.DiscountPlusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountPlusRepo extends JpaRepository<DiscountPlusEntity,Long> {
    @Query("SELECT dp FROM DiscountPlusEntity dp WHERE dp.productDiscountId =:id")
    public DiscountPlusEntity getByDiscountProductId(@Param("id") Long id);
}
