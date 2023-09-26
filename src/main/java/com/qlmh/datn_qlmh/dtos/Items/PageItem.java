package com.qlmh.datn_qlmh.dtos.Items;

import lombok.Data;

@Data
public class PageItem {
    private int pageNumber;
    private int pageSize;

    public PageItem(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
