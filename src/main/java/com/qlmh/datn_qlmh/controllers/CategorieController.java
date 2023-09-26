package com.qlmh.datn_qlmh.controllers;

import com.qlmh.datn_qlmh.dtos.request.CategoriesReq;
import com.qlmh.datn_qlmh.dtos.response.CategoriesResp;
import com.qlmh.datn_qlmh.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategorieController {
    @Autowired
    CategoriesService categoriesService;

    @GetMapping()
    public ResponseEntity<List<CategoriesResp>> getAll() {

        return ResponseEntity.ok(categoriesService.getAllCategories());
    }

    @PostMapping()
    public ResponseEntity<CategoriesReq> create(@RequestBody CategoriesReq categoriesReq) {

        return ResponseEntity.ok(categoriesService.createCategory(categoriesReq));
    }

    @PutMapping()
    public ResponseEntity<CategoriesReq> update(@RequestBody CategoriesReq categoriesReq) {
        return ResponseEntity.ok(categoriesService.updateCategory(categoriesReq));
    }


}
