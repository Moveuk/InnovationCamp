package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class ExpiredTokenException extends RuntimeException {
    private ErrorCode errorCode;

    public ExpiredTokenException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
