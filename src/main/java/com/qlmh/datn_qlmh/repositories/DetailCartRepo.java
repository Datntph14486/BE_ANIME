package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.DetailCartEntity;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface DetailCartRepo extends JpaRepository<DetailCartEntity, Integer>, JpaSpecificationExecutor<DetailCartEntity> {
    DetailCartEntity findByCartIdAndProductId(Integer cartId, Integer productId);
    List<DetailCartEntity> findAllByCartId(Integer cartId);
    Long countByCartId(Integer id);
    List<DetailCartEntity> findByCreateDateBefore(Date date);
}