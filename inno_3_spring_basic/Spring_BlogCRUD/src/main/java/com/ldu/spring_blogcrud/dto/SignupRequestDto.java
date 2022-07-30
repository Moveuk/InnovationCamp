package com.ldu.spring_blogcrud.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SignupRequestDto {
    private String nickname;
    private String password;
    private String password_ck;
}
