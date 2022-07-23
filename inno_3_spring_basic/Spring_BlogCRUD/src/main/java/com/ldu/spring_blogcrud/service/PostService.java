package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 글 목록 리스트 받아오기
    public List<Post> getPostsList() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        return posts;
    }

    // 글 조회
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

    // 글 수정
    @Transactional
    public Long update(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다."));
        post.update(postRequestDto);
        return post.getId();
    }

    public Long delete(Long id) {
        postRepository.deleteById(id);
        return id;
    }
}
