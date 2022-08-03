package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class PutUnauthorizedException extends RuntimeException {
    private ErrorCode errorCode;

    public PutUnauthorizedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
