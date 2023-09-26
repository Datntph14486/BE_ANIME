package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.common.PageData;
import com.qlmh.datn_qlmh.dtos.Items.SearchFilterPage;
import com.qlmh.datn_qlmh.dtos.request.ProductItems;
import com.qlmh.datn_qlmh.dtos.request.ProductReq;
import com.qlmh.datn_qlmh.dtos.response.ProductResp;
import com.qlmh.datn_qlmh.dtos.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResp> getAllProducts();
    List<ProductResp> getProductByTitle(Integer title);
    ProductReq create(ProductReq product);
    ProductReq update(ProductReq product);
    ProductReq updateStatus(Integer id, int status);
    ProductResp getByID(Integer id);
    PageData<ProductResponse> getAll(ProductItems request, int pageNum, int pageSize, String sortBy, String sortDesc);
    ProductResponse detailProduct(Integer id);
}