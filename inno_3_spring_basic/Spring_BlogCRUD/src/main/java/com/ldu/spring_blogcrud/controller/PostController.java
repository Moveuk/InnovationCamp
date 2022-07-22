package com.ldu.spring_blogcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @GetMapping(path = "/api/posts")
    public void getPosts() {

    }
}
