package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @GetMapping(path = "/api/posts")
    public List<PostRequestDto> getPosts() {
        return postService.getPostsList();
    }
}
