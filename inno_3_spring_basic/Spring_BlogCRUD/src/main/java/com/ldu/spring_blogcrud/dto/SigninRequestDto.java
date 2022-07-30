package com.ldu.spring_blogcrud.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SigninRequestDto {
    private String nickname;
    private String password;
}
