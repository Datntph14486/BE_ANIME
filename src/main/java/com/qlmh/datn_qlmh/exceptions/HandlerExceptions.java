package com.qlmh.datn_qlmh.exceptions;

import com.qlmh.datn_qlmh.dtos.Response;
import com.qlmh.datn_qlmh.dtos.ResponseTemplate;
import com.qlmh.datn_qlmh.exceptions.model.ErrorResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Calendar;
import java.util.List;

@RestControllerAdvice
@Configuration
public class HandlerExceptions {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> notFoundHandler(NotFoundException exception){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(), ResponseTemplate.NOT_FOUND(exception.getMessage())));
    }
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<Response> invalidArgumentHandler(InvalidArgumentException exception){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),new ErrorResponse(exception.getCode(),exception.getMessage())));
    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Response> entityAlreadyExistsHandler(EntityAlreadyExistsException exception){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),new ErrorResponse(exception.getCode(),exception.getMessage())));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> methodArgumentNotValidHandler(MethodArgumentNotValidException exception){
//        List<FieldError> errors = exception.getFieldErrors();
//        System.out.println(errors.get(0).getDefaultMessage());
//        System.out.println(errors.get(errors.size()-1).getDefaultMessage());
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),new ErrorResponse(HttpStatus.BAD_REQUEST.value(),exception.getFieldErrors().get(0).getDefaultMessage() )));
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response> accessDeniedExceptionHandler(AccessDeniedException exception){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),ResponseTemplate.ACCESS_DENIED));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> badCredentialsExceptionHandler(BadCredentialsException exception){
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),new ErrorResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handler(Exception exception){
        exception.printStackTrace();
        return ResponseEntity.ok(new Response(Calendar.getInstance().getTime(),ResponseTemplate.UNDEFINED_ERROR));
    }

}
