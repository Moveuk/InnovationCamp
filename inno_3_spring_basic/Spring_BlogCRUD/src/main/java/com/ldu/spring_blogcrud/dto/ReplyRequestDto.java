package com.ldu.spring_blogcrud.dto;

import com.ldu.spring_blogcrud.entity.Reply;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "댓글 삽입에 필요한 정보를 담는다.")
public class ReplyRequestDto {

//    @ApiModelProperty(value = "게시글 번호", example = "게시글 번호 정보 입니다.",dataType = "String")
    private Long postId;
//    @ApiModelProperty(value = "작성자", example = "작성자입니다.",dataType = "String")
    private String author;
    @ApiModelProperty(value = "글 내용", example = "글 내용입니다.",dataType = "String")
    private String content;

}
