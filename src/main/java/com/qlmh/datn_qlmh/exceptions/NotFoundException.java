package com.qlmh.datn_qlmh.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{
    public NotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
