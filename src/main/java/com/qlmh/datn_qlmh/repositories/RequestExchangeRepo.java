package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.RequestExchangeProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestExchangeRepo extends JpaRepository<RequestExchangeProductEntity,Integer> {
}
