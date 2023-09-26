package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {
    @Query("select p from ProductEntity p where  p.title =?1")
    List<ProductEntity> getProductsByTitle(Integer title);
    ProductEntity findByIdAndStatus(Integer id, int status);
    @Query("SELECT p FROM ProductEntity p WHERE p.status =:st ")
    Page<ProductEntity> findAllByStatus(@Param("st") int status, Pageable pageable);
    @Query("SELECT se,me,ce FROM com.qlmh.datn_qlmh.entities.ProductEntity pe " +
            "INNER JOIN com.qlmh.datn_qlmh.entities.SeriesEntity se ON pe.seriesID=se.id " +
            "INNER JOIN com.qlmh.datn_qlmh.entities.ManufacturerEntity me ON pe.manufacturerID=me.id " +
            "INNER JOIN com.qlmh.datn_qlmh.entities.CategoriesEntity ce ON pe.categoryId=ce.id WHERE pe.id=:id")
    List<Object[]> getCustom(@Param("id") Integer id);
}
