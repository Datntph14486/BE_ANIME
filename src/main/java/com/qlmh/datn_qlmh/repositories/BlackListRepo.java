package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.BlackListEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlackListRepo extends JpaRepository<BlackListEntity,Long> {
//    @Query("SELECT bl FROM BlackListEntity bl WHERE bl.productId := id")
//    List<BlackListEntity> getByProductId(@Param("id") Long id);
}
