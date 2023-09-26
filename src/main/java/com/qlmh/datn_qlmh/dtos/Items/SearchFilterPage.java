package com.qlmh.datn_qlmh.dtos.Items;

import lombok.Data;

@Data
public class SearchFilterPage {
    private PageItem page;
    private FilterItems filter;
    private String search;
    private String searchV2;
    private String fromDate;
    private String toDate;

}
