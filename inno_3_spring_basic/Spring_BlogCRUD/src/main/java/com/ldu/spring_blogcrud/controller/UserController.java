package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.SignupRequestDto;
import com.ldu.spring_blogcrud.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "회원 관리 컨트롤러")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/api/users/signup")
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
    }
}
