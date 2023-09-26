package com.qlmh.datn_qlmh.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.qlmh.datn_qlmh.constants.Constant;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // thuoc tinh nao null se bo qua , khong duoc Serialization
public class Response {
    String at;
    ErrorResponse infor_response;
    Object data;

    public Response(Date current, ErrorResponse error){
        this.at = DateFormatUtils.format(current, Constant.DATE_FORMAT);
        this.infor_response = error;
    }

    public Response(Date current, ErrorResponse error, Object data){
        this.at = DateFormatUtils.format(current, Constant.DATE_FORMAT);
        this.infor_response = error;
        this.data = data;
    }
}
