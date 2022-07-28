package com.ldu.spring_blogcrud.common.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponseDto {

    // 작은 프로젝트에서 에러코드가 따로 필요한가?

    private int status;
    private String message;
    private String cod;

    public ErrorResponseDto(int status, String message, String cod) {
        this.status = status;
        this.message = message;
        this.cod = cod;
    }
}
