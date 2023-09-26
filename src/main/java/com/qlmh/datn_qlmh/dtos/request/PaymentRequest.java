package com.qlmh.datn_qlmh.dtos.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentRequest implements Serializable {
    private Integer id;
    private String codeBill;
    private String transactionNo;
    private String payDate;
}
