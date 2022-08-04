package com.ldu.spring_blogcrud.entity;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "글쓴이는 필수입니다.")
    private String author;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public Post(String title, String username, String password, String content) {
        this.title = title;
        this.author = username;
        this.password = password;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
