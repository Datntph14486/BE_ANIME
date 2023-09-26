package com.qlmh.datn_qlmh.dtos.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.qlmh.datn_qlmh.dtos.Items.DetailCartItems;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse{
    private Integer cartId;
    private String userName;
    private BigDecimal totalPrice;
    private int amountProduct;
    private BigDecimal discountPrice;
    private int typeOfBill;
    private List<DetailCartItems> items;
}
