package com.ldu.spring_blogcrud.entity;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.dto.ReplyRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
//    @NotBlank(message = "postID는 필수입니다.")
    private Long postId;

    @Column(nullable = false)
    @NotBlank(message = "작성자는 필수입니다.")
    private String author;

    @Column(nullable = false)
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    public void update(String content) {
        this.content = content;
    }

    public Reply(Long postId, String author, String content) {
        this.postId = postId;
        this.author = author;
        this.content = content;
    }
}