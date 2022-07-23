package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
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
    public List<PostRequestDto> getPosts() {
        return postService.getPostsList();
    }

    @GetMapping(path = "/api/posts/{id}")
    public Post getPosts(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping(path = "/api/posts")
    public Long createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        System.out.println(postRequestDto.getId() == null);
        return postService.create(postRequestDto);
    }

}
