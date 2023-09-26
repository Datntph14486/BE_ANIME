package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.dtos.request.BillRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BillRespponses {
   private List<BillRequest> items = new ArrayList<>();
}
