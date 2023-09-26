package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.DeliveryInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryInvoiceRepo extends JpaRepository<DeliveryInvoiceEntity, Integer>, JpaSpecificationExecutor<DeliveryInvoiceEntity> {
    DeliveryInvoiceEntity findByBillId(Integer billId);

}