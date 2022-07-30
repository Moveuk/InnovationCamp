package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;

@Getter
public class UserNotMatchRegexException extends RuntimeException {
    private ErrorCode errorCode;

    public UserNotMatchRegexException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
