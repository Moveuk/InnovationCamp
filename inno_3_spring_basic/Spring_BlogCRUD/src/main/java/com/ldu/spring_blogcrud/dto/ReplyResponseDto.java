package com.ldu.spring_blogcrud.dto;

import com.ldu.spring_blogcrud.entity.Reply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReplyResponseDto {

    @ApiModelProperty(example = "Reply Entity Id값")
    private Long id;
    @ApiModelProperty(example = "Post Entity Id값")
    private Long postId;
    @ApiModelProperty(example = "댓글 작성자")
    private String author;
    @ApiModelProperty(example = "댓글 내용")
    private String content;
    @ApiModelProperty(example = "글 생성날짜")
    private LocalDateTime createdAt;
    @ApiModelProperty(example = "글 수정날짜")
    private LocalDateTime modifiedAt;

    public ReplyResponseDto(Reply reply) {
        this.id = reply.getId();
        this.postId = reply.getPostId();
        this.author = reply.getAuthor();
        this.content = reply.getContent();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
