package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepo extends JpaRepository<CategoriesEntity, Integer> {

}
