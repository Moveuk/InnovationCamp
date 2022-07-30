package com.ldu.spring_blogcrud.entity;

import com.ldu.spring_blogcrud.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "USERS")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "회원 이름은 공백일 수 없습니다.")
    private String nickname;

    @Column(nullable = false)
    @NotBlank(message = "회원 비밀번호는 공백일 수 없습니다.")
    private String password;

    public User(SignupRequestDto signupRequestDto) {
        this.nickname = signupRequestDto.getNickname();
        this.password = signupRequestDto.getPassword();
    }
}
