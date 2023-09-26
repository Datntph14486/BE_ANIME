package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.RateEntity;
import com.qlmh.datn_qlmh.entities.ReturnRequestDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReturnRequestDetailRepo extends JpaRepository<ReturnRequestDetailEntity,Integer> {
}
