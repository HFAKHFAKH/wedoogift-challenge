package com.wedoogift.challenge.exception;

import com.wedoogift.challenge.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleError(ApplicationException ex) {
        return ResponseEntity.status(ex.getCode()).body(new ErrorDto(ex.getMessage()));
    }
}
