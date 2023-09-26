package com.qlmh.datn_qlmh.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qlmh.datn_qlmh.entities.ExchangeProductEntity;
import com.qlmh.datn_qlmh.entities.RequestExchangeProductDetailEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestExchangeProductReq {

    private Integer id;
    private String Description;
    private String Status;
    private String UserName;

}
