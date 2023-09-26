package com.qlmh.datn_qlmh.dtos.Items;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qlmh.datn_qlmh.dtos.response.BaseResponse;
import com.qlmh.datn_qlmh.utilities.Status;
import lombok.Data;

import java.util.Date;
@Data
public class BaseItems {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING,timezone = "Asia/Saigon")
    private Date createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING,timezone = "Asia/Saigon")
    private Date updateDate;
    private String createBy;
    private String updateBy;
}
