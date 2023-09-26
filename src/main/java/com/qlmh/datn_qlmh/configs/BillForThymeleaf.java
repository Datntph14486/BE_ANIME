package com.qlmh.datn_qlmh.configs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class BillForThymeleaf {
    String path = "mailbill_";
    Map<String,Object> variable;
    public BillForThymeleaf(Map<String,Object> map){
        this.variable = map;
    }
}
