package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 글 목록 리스트 받아오기
    public List<PostRequestDto> getPostsList() {
        List<Post> posts = postRepository.findAll();
        List<PostRequestDto> postList = new ArrayList<>();
        posts.stream().forEach(post -> postList.add(new PostRequestDto(post)));
        return postList;
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow((() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다.")
        ));
    }

    // 글 등록
    @Transactional
    public Long create(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return post.getId();
    }
}
