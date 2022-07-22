package com.ldu.spring_blogcrud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class postRequestDto {

    private Long id;
    private String title;
    private String username;
    private String password;
    private String content;
}
