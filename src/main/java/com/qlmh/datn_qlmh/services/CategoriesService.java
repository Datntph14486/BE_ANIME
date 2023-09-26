package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.request.CategoriesReq;
import com.qlmh.datn_qlmh.dtos.response.CategoriesResp;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
     CategoriesReq createCategory(CategoriesReq categoriesReq);
     CategoriesReq updateCategory(CategoriesReq categoriesReq);
     List<CategoriesResp> getAllCategories();
     Optional<CategoriesReq> getCategoriesID(Integer categoryID);
     CategoriesReq updateActive(CategoriesReq categoriesReq);
}
