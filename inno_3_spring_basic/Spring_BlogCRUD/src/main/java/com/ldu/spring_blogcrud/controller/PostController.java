package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.dto.PostResponseDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/api/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPostsList();
    }

    @GetMapping(path = "/api/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping(path = "/api/posts")
    public Long createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        return postService.create(postRequestDto);
    }

    @PutMapping(path = "/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        return postService.update(id, postRequestDto);
    }

    @DeleteMapping(path = "/api/posts/{id}")
    public Long deletePost(@PathVariable Long id) {
        return postService.delete(id);
    }

    @PostMapping(path = "/api/post/{id}/check")
    public Boolean checkPostPassword(@PathVariable Long id,@RequestBody PostRequestDto postRequestDto) {
        return postService.checkPassword(id,postRequestDto);
    }
}
