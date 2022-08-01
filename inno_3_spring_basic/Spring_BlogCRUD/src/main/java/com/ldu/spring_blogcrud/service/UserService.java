package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.common.exceptions.ErrorCode;
import com.ldu.spring_blogcrud.common.exceptions.NicknameDuplicatedException;
import com.ldu.spring_blogcrud.common.exceptions.UserNotMatchRegexException;
import com.ldu.spring_blogcrud.dto.SignupRequestDto;
import com.ldu.spring_blogcrud.entity.User;
import com.ldu.spring_blogcrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void signup(SignupRequestDto signupRequestDto) {

        //정규표현식 체크
        Pattern nicknamePattern = Pattern.compile("^[0-9a-zA-Z]{4,12}$");
        Pattern passwordPattern = Pattern.compile("^[0-9a-z]{4,32}$");

        if (!nicknamePattern.matcher(signupRequestDto.getNickname()).matches()) {
            throw new UserNotMatchRegexException("닉네임은 최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성해 주세요.", ErrorCode.NOT_MATCH_REGEX);
        }
        if (!passwordPattern.matcher(signupRequestDto.getPassword()).matches()) {
            throw new UserNotMatchRegexException("닉네임은 최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성해 주세요.", ErrorCode.NOT_MATCH_REGEX);
        }
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getPassword_ck())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인 값이 일치하지 않습니다.");
        }

        //중복 체크
        Optional<User> found = userRepository.findByNickname(signupRequestDto.getNickname());
        if (found.isPresent()) {
            throw new NicknameDuplicatedException("중복인 닉네임은 사용하실 수 없습니다.", ErrorCode.NICKNAME_DUPLICATED);
        }

        //패스워드 인코드
        signupRequestDto.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        userRepository.save(new User(signupRequestDto));
    }

}
