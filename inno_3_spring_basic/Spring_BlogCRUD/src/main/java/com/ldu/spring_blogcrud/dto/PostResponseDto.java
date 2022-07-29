package com.ldu.spring_blogcrud.dto;

import com.ldu.spring_blogcrud.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    @ApiModelProperty(example = "Post Entity Id값")
    private Long id;
    @ApiModelProperty(example = "글제목")
    private String title;
    @ApiModelProperty(example = "글쓴이")
    private String author;
    @ApiModelProperty(example = "글내용")
    private String content;
    @ApiModelProperty(example = "글 생성날짜")
    private LocalDateTime createdAt;
    @ApiModelProperty(example = "글 수정날짜")
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
