package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class PostUnauthorizedException extends RuntimeException {
    private ErrorCode errorCode;

    public PostUnauthorizedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
