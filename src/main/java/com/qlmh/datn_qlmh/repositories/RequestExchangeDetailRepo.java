package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.RequestExchangeProductDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestExchangeDetailRepo extends JpaRepository<RequestExchangeProductDetailEntity,Integer> {

    @Query("SELECT  R FROM RequestExchangeProductDetailEntity R WHERE R.exchangeProductEntity.id=?1")
    List<RequestExchangeProductDetailEntity> getByRequestId(Integer id);
}
