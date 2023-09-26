package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.ProductDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;


public interface ProductDiscountRepo extends JpaRepository<ProductDiscountEntity,Long> {
//    @Query("SELECT pd FROM ProductDiscountEntity pd WHERE pd.discountEntity.id := id")
//    List<ProductDiscountEntity> getByDiscountId(@Param("id") Long id);
    List<ProductDiscountEntity> findByProductIdAndStatus(Integer Product, boolean status);
    @Query("SELECT pd FROM ProductDiscountEntity pd WHERE pd.discountId =:id")
    List<ProductDiscountEntity> getByDiscountId(@Param("id") Long id);
    @Query("SELECT count(pd) FROM ProductDiscountEntity pd WHERE pd.discountId =:id AND pd.productId =:pid and  pd.status=1")
    Long getByDiscountIdAndProductId(@Param("id") Long id,@Param("pid") Integer pid);

}
