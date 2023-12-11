package com.wedoogift.challenge.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final Integer code;

    public ApplicationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
