package com.ldu.spring_blogcrud.dto;

import com.ldu.spring_blogcrud.common.exceptions.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private ErrorCode errorCode;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(String code, String message, ErrorCode errorCode) {
        return new ResponseDto<>(false, null, errorCode);
    }

}