package com.qlmh.datn_qlmh.services.impl;

import com.qlmh.datn_qlmh.dtos.request.CategoriesReq;
import com.qlmh.datn_qlmh.dtos.response.CategoriesResp;
import com.qlmh.datn_qlmh.entities.CategoriesEntity;
import com.qlmh.datn_qlmh.repositories.CategoriesRepo;
import com.qlmh.datn_qlmh.services.CategoriesService;
import com.qlmh.datn_qlmh.services.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    CategoriesRepo categoriesRepo;
    @Autowired
    Mapper mapper;

    @Override
    public CategoriesReq createCategory(CategoriesReq categoriesReq) {
        CategoriesEntity categoriesEntity = mapper.toCategoriesEntity(categoriesReq);
        categoriesEntity = categoriesRepo.save(categoriesEntity);
        return mapper.toCategoriesReq(categoriesEntity);
    }

    @Override
    public CategoriesReq updateCategory(CategoriesReq categoriesReq) {
        if (categoriesReq.getId() == null) {
            return null;
        }
        CategoriesEntity categoriesEntity = mapper.toCategoriesEntity(categoriesReq);
        categoriesEntity = categoriesRepo.save(categoriesEntity);
        return mapper.toCategoriesReq(categoriesEntity);
    }

    @Override
    public List<CategoriesResp> getAllCategories() {

        return categoriesRepo.findAll().stream().map((entity) -> mapper.toCategoriesResp(entity)).collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriesReq> getCategoriesID(Integer categoryID) {
        return null;
    }

    @Override
    public CategoriesReq updateActive(CategoriesReq categoriesReq) {
        return null;
    }
}
