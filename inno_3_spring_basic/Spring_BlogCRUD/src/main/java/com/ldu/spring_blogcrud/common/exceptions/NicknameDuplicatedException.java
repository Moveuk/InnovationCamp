package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class NicknameDuplicatedException extends RuntimeException {
    private ErrorCode errorCode;

    public NicknameDuplicatedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
