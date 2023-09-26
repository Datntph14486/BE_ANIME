package com.qlmh.datn_qlmh.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    protected int code;
    protected String message;

    public CustomException(String message,int code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
