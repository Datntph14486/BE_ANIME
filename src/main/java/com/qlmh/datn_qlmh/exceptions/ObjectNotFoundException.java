package com.qlmh.datn_qlmh.exceptions;

import com.qlmh.datn_qlmh.constants.ResponseStatusConstant;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;

public class ObjectNotFoundException extends ErrorResponse {

    public ObjectNotFoundException(ResponseStatusConstant e) {
        super(e.getCode(), e.getMessage());
    }
}
