package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.DiscountEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DiscountRepo extends JpaRepository<DiscountEntity,Long>,JpaSpecificationExecutor<DiscountEntity> {
    DiscountEntity findByIdAndStatus(Long id, Integer status);
    DiscountEntity findByIdAndDiscountType(Long id, Byte discountType);
    @Query("SELECT de FROM DiscountEntity de WHERE de.status =:status AND :current BETWEEN de.discountStart AND de.discountEnd")
    public List<DiscountEntity> getDiscountActive(Sort sort, @Param("status") Integer status, @Param("current") Date current);

}
