package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.SignupRequestDto;
import com.ldu.spring_blogcrud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "회원 관리 컨트롤러")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ApiResponses({
            @ApiResponse(code=200, message="회원가입 완료"),
            @ApiResponse(code=400, message="닉네임 혹은 비밀번호 정규표현식 오류"),
            @ApiResponse(code=403, message="중복된 이름 작성 금지"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @PostMapping(path = "/api/signup")
    @ApiOperation(value = "회원가입", notes = "닉네임과 비밀번호를 입력하여 회원가입을 한다.")
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }
}
