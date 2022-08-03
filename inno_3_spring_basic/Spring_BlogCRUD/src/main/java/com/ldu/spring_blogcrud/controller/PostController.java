package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.dto.PostResponseDto;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;
import com.ldu.spring_blogcrud.service.PostService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Api(value = "게시글 컨트롤러")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @ApiResponses({
            @ApiResponse(code=200, message="글목록 조회 완료"),
            @ApiResponse(code=401, message="조회 권한 없음. 로그인 필요"),
            @ApiResponse(code=500, message="서버 에러")
    })
//    @Secured("ROLE_ADMIN") // Role에 맞는 사람만 접근하도록 하는 어노테이션
    @GetMapping(path = "/api/posts")
    @ApiOperation(value = "전체 게시글 목록 조회", notes = "전체 게시글 목록을 조회한다.")
    public List<PostResponseDto> getPosts() {
        return postService.getPostsList();
    }

    @ApiResponses({
            @ApiResponse(code=200, message="글 조회 완료"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @GetMapping(path = "/api/posts/{id}")
    @ApiOperation(value = "게시글 조회", notes = "게시글을 조회한다.")
    @ApiImplicitParam(name = "id", value = "Post Entity Id값")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="글 작성 완료"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @PostMapping(path = "/api/posts")
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성한다.")
    public Long createPost(@Valid @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postRequestDto.setAuthor(userDetails.getUsername());
        return postService.create(postRequestDto);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="글 수정 완료"),
            @ApiResponse(code=403, message="수정 권한 없음. 글 비밀번호 불일치"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @PutMapping(path = "/api/posts/{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ApiImplicitParam(name = "id", value = "Post Entity Id값")
    public Long updatePost(@PathVariable Long id, @RequestBody @ApiIgnore PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.update(id, postRequestDto, userDetails);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="글 삭제 완료"),
            @ApiResponse(code=403, message="삭제 권한 없음. 글 비밀번호 불일치"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @DeleteMapping(path = "/api/posts/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiImplicitParam(name = "id", value = "Post Entity Id값")
    public Long deletePost(@PathVariable Long id, @RequestBody @ApiIgnore PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.delete(id, postRequestDto, userDetails);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="글 비밀번호 확인"),
            @ApiResponse(code=400, message="조회 권한 없음."),
            @ApiResponse(code=500, message="서버 에러")
    })
    @PostMapping(path = "/api/post/{id}")
    @ApiOperation(value = "비밀번호 확인", notes = "비밀 게시글을 조회 권한을 비밀번호를 통해 인가를 확인한다.")
    @ApiImplicitParam(name = "id", value = "Post Entity Id값")
    public Boolean checkPostPassword(@PathVariable Long id, @RequestBody @ApiIgnore PostRequestDto postRequestDto) {
        return postService.checkPassword(id, postRequestDto);
    }
}
