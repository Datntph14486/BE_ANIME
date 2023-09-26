package com.qlmh.datn_qlmh.dtos.Items;

import com.qlmh.datn_qlmh.dtos.request.DiscountReq;
import com.qlmh.datn_qlmh.dtos.request.VoucherReq;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailCartItems {
    private Integer id;
    private Integer productId;
    private BigDecimal price;
    private BigDecimal priceSales;
    private List<Long> discountId;
    private int amountProduct;
    private int amount;
    private float height;
    private float weight;
    private List<ImgProductItems> productImg;
    private String productName;
    private String url;
    private int typeOrder;

}
