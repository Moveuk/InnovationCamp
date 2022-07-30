package com.ldu.spring_blogcrud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ApiModel(value = "로그인에 필요한 정보를 담는다.")
public class SigninRequestDto {
    @ApiModelProperty(value = "닉네임", example = "닉네임", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "비밀번호", example = "비밀번호", dataType = "String")
    private String password;
}
