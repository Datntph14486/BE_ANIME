package com.qlmh.datn_qlmh.dtos.Items;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class FilterItems {
    private String sortBy;
    private String sortDirection;

    public FilterItems(String sortBy, String sortDirection) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }
}
