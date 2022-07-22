package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 글 목록 리스트 받아오기
    public List<PostRequestDto> getPostsList() {
        List<Post> posts = postRepository.findAll();
        List<PostRequestDto> postList = new ArrayList<>();
        posts.stream().forEach(post -> postList.add(new PostRequestDto(post)));
        return postList;
    }

    // 글 등록
    public Post insertPost() {


        return null;
    }
}
