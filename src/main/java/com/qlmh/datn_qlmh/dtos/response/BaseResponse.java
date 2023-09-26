package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.utilities.Status;
import lombok.Data;

@Data
public class BaseResponse {
    private Status status=new Status();
}
