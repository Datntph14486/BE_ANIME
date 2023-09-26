package com.qlmh.datn_qlmh.services;

import com.qlmh.datn_qlmh.dtos.Items.PageItem;
import com.qlmh.datn_qlmh.dtos.request.CartRequest;
import com.qlmh.datn_qlmh.dtos.response.CartResponse;
import com.qlmh.datn_qlmh.dtos.response.DetailCartResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICartService {
DetailCartResponse addToCart(CartRequest request);
DetailCartResponse update(Integer id, int amount);
DetailCartResponse delete(Integer id);
DetailCartResponse deleteAllCart(String userName);

DetailCartResponse deleteListCart(List<Integer> id);
CartResponse findById(String userName);
Page<CartResponse> findAll(PageItem items);

Long count(String userName);

}
