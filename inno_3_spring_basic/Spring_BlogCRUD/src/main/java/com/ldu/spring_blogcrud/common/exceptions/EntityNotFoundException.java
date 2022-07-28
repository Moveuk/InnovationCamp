package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private ErrorCode errorCode;

    public EntityNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
