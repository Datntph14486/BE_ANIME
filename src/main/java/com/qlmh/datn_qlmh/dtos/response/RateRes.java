
package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.entities.RateImgEntity;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RateRes {
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
    protected Date createDate;
    protected Date updateDate;
    protected String createBy;
    protected String updateBy;
    private String avatar;
    private Boolean rightToEdit;
    private List<RateImgEntity> imgs;

}

