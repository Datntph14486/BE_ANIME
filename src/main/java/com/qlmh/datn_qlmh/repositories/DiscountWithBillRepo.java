package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.DiscountWithBillEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface DiscountWithBillRepo extends JpaRepository<DiscountWithBillEntity,Long>, JpaSpecificationExecutor<DiscountWithBillEntity> {
    DiscountWithBillEntity findByMinPriceAndStatus(BigDecimal price, Boolean status);
    DiscountWithBillEntity findByMinPriceGreaterThanEqualAndStatus(BigDecimal price, Boolean status);
    @Query("SELECT de FROM DiscountWithBillEntity de WHERE de.status =:status AND :current BETWEEN de.discountStart AND de.discountEnd")
    public List<DiscountWithBillEntity> getDiscountActive(Sort sort, @Param("status") Integer status, @Param("current") Date current);
}
