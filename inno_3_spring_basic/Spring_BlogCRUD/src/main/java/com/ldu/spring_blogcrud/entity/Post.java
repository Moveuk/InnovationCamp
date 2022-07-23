package com.ldu.spring_blogcrud.entity;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    public Post(PostRequestDto postRequestDto) {
        this.id = postRequestDto.getId();
        this.title = postRequestDto.getTitle();
        this.author = postRequestDto.getAuthor();
        this.password = postRequestDto.getPassword();
        this.content = postRequestDto.getContent();
    }

    public Post(String title, String username, String password, String content) {
        this.title = title;
        this.author = username;
        this.password = password;
        this.content = content;
    }

    public void update(PostRequestDto postRequestDto) {
        this.id = postRequestDto.getId();
        this.title = postRequestDto.getTitle();
        this.author = postRequestDto.getAuthor();
        this.password = postRequestDto.getPassword();
        this.content = postRequestDto.getContent();
    }
}
