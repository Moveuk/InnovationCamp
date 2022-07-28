package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class DeleteUnauthorizedException extends RuntimeException {
    private ErrorCode errorCode;

    public DeleteUnauthorizedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
