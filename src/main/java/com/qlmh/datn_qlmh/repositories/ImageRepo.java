package com.qlmh.datn_qlmh.repositories;

import com.qlmh.datn_qlmh.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.List;

public interface ImageRepo extends JpaRepository<ImageEntity, Integer> {
//    @Query("Select image from imageEntity image where image.productID = :productID")
//    List<imageEntity> getImageByProductID(@Param("productID") Integer productID);

    @Query("Select image from ImageEntity image where image.productID = ?1")
    List<ImageEntity> getImageByProductID(Integer productID);
    List<ImageEntity> findByProductID(Integer productId);

}
