package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.PreOderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PreOderEntityRepo extends JpaRepository<PreOderEntity, Integer>, JpaSpecificationExecutor<PreOderEntity> {
}