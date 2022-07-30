package com.ldu.spring_blogcrud.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@ApiModel(description = "회원가입에 필요한 정보를 담는다.")
public class SignupRequestDto {
    @ApiModelProperty(value = "닉네임", example = "닉네임", dataType = "String")
    private String nickname;
    @ApiModelProperty(value = "비밀번호", example = "비밀번호", dataType = "String")
    private String password;
    @ApiModelProperty(value = "비밀번호 확인", example = "비밀번호 확인", dataType = "String")
    private String password_ck;
}
