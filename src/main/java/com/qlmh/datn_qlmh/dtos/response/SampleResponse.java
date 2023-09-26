package com.qlmh.datn_qlmh.dtos.response;

import com.qlmh.datn_qlmh.utilities.MessageUtils;
import lombok.Data;

@Data
public class SampleResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> SampleResponse<T> success(T body,String message) {
        SampleResponse<T> response = new SampleResponse<>();
        response.setMessage(MessageUtils.get("",message));
        response.setData(body);
        return response;
    }
}
