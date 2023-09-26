package com.qlmh.datn_qlmh.dtos.request;

import com.qlmh.datn_qlmh.dtos.Items.BaseItems;
import com.qlmh.datn_qlmh.entities.DetailBillEntity;
import com.qlmh.datn_qlmh.entities.DiscountEntity;
import com.qlmh.datn_qlmh.entities.ProductEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DetailBillResponse extends BaseItems {
    private Integer id;
    private BigDecimal price;
    private BigDecimal priceSale;
    private int amount;
    private Integer productId;
    private String productName;
    private BigDecimal deposit;
    private String categoryName;
    private String url;
    private List<DiscountEntity> discountId;
    private int TypeOfBill;
    private BigDecimal customerMoney;
    private float weight;
    private float height;
    private boolean isRate;
    private int productStatus;
    public DetailBillResponse(DetailBillEntity detailBill, ProductEntity productEntity){
        this.id = detailBill.getId();
        this.price = detailBill.getPrice();
        this.priceSale = detailBill.getPriceSale();
        this.weight = productEntity.getWeight();
        this.height = productEntity.getHeight();
        this.amount = detailBill.getAmount();
        this.productId=detailBill.getProductId();
        this.productName = productEntity.getFigure();
        this.TypeOfBill = detailBill.getTypeOfBill();
        this.productStatus = productEntity.getStatus();
    }
}
