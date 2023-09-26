package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface BillRepo extends JpaRepository<BillEntity, Integer>, JpaSpecificationExecutor<BillEntity> {
    List<BillEntity> findByUsername(String userName);
    List<BillEntity> findByUsernameAndStatus(String userName, BillEntity.StatusEnum status);
    Page<BillEntity> findAllByUsername(String userName, Pageable pageable);
    Page<BillEntity> findAllByStatus(BillEntity.StatusEnum status, Pageable pageable);
    BillEntity findByCodeBillAndStatus(String code, BillEntity.StatusEnum status);


}