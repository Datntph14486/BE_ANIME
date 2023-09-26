package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<PaymentEntity, Integer>, JpaSpecificationExecutor<PaymentEntity> {
    PaymentEntity findByTransactionNo(String transaction);
    PaymentEntity findByCodeBill(String code);
}
