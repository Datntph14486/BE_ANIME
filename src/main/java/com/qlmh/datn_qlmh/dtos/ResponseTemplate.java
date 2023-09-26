package com.qlmh.datn_qlmh.dtos;

import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import org.springframework.http.HttpStatus;

public class ResponseTemplate {
    private static final Byte ERROR_CODE = 99;
    private static final Byte LOGIN_FAILED_CODE = 100;
    public static ErrorResponse SUCCESS = new ErrorResponse(HttpStatus.OK.value(), "OK");
    public static ErrorResponse CREATED = new ErrorResponse(HttpStatus.CREATED.value(),"CREATED");
    public static ErrorResponse UNDEFINED_ERROR = new ErrorResponse(ERROR_CODE,"GENERIC ERROR");
    public static ErrorResponse ACCESS_DENIED = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "ACCESS DENIED");
    public static ErrorResponse LOGIN_FAILED = new ErrorResponse(LOGIN_FAILED_CODE, "USERNAME OR PASSWORD IS WRONG");
    public static ErrorResponse NOT_FOUND(String message) {
        return new ErrorResponse(404,message);
    }
}
