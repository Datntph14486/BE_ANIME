package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CartRepo extends JpaRepository<CartEntity, Integer>, JpaSpecificationExecutor<CartEntity> {
    Optional<CartEntity> findByUserName(String userId);
}