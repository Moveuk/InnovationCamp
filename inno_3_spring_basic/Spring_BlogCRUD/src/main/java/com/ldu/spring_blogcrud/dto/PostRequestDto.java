package com.ldu.spring_blogcrud.dto;

import com.ldu.spring_blogcrud.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private Long id;
    private String title;
    private String author;
    private String password;
    private String content;

    public PostRequestDto(String title, String username, String password, String content) {
        this.title = title;
        this.author = username;
        this.password = password;
        this.content = content;
    }

    public PostRequestDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.password = post.getPassword();
        this.content = post.getContent();
    }
}
