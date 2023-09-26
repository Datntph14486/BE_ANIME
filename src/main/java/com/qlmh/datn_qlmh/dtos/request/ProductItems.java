package com.qlmh.datn_qlmh.dtos.request;

import lombok.Data;

import java.util.List;

@Data
public class ProductItems {
    private List<Integer> categoryIds;
    private List<Integer> manufacturerID;
    private List<Integer> seriesID;
    private List<String> ratio;
    private List<Double> minPrice;
    private List<Double> maxPrice;
    private String name;
    private Integer title;
    private Integer available;

}
