package com.ldu.spring_blogcrud.dto;

import com.ldu.spring_blogcrud.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "글 삽입에 필요한 정보를 담는다.")
public class PostRequestDto {

    @ApiModelProperty(value = "글 제목", example = "글 제목입니다.",dataType = "String")
    private String title;
//    @ApiModelProperty(value = "글쓴이", example = "글쓴이입니다.",dataType = "String")
    private String author;
    @ApiModelProperty(value = "비밀번호", example = "비밀번호입니다.",dataType = "String")
    private String password;
    @ApiModelProperty(value = "글 내용", example = "글 내용입니다.",dataType = "String")
    private String content;

    // api에서 넘어올때 생성자가 없으면 RequestBody를 통해 못잡나봄.
//    public PostRequestDto(String title, String username, String password, String content) {
//        this.title = title;
//        this.author = username;
//        this.password = password;
//        this.content = content;
//    }

    public PostRequestDto(Post post) {
        this.title = post.getTitle();
        this.author = post.getAuthor();
        this.password = post.getPassword();
        this.content = post.getContent();
    }
}
