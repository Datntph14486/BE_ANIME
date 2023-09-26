package com.qlmh.datn_qlmh.dtos.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateReq {
    private Integer id;
    private Integer rate;
    private String comment;
    private boolean color;
    private boolean wrongProduct;
    private boolean lackOfAccessories;
    private boolean material;
    private Integer detailBillID;
    private Integer productID;
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isWrongProduct() {
        return wrongProduct;
    }

    public void setWrongProduct(boolean wrongProduct) {
        this.wrongProduct = wrongProduct;
    }

    public boolean isLackOfAccessories() {
        return lackOfAccessories;
    }

    public void setLackOfAccessories(boolean lackOfAccessories) {
        this.lackOfAccessories = lackOfAccessories;
    }

    public boolean isMaterial() {
        return material;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public Integer getDetailBillID() {
        return detailBillID;
    }

    public void setDetailBillID(Integer detailBillID) {
        this.detailBillID = detailBillID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
