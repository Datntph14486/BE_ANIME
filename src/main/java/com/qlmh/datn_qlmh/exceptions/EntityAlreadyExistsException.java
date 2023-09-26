package com.qlmh.datn_qlmh.exceptions;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends CustomException{
    public EntityAlreadyExistsException(String message){
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
