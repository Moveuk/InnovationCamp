package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.common.exceptions.*;
import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.dto.PostResponseDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 글 목록 리스트 받아오기
    public List<PostResponseDto> getPostsList() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostResponseDto> postList = new ArrayList<>();
        posts.stream().forEach(post -> postList.add(new PostResponseDto(post)));
        return postList;
    }

    // 글 조회
    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow((() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND))
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
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        if (!post.getPassword().equals(postRequestDto.getPassword())) {
            throw new PostUnauthorizedException("비밀번호가 틀립니다.",ErrorCode.POST_UNAUTHORIZED);
        }
        post.update(postRequestDto);
        return post.getId();
    }

    public Long delete(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        if (post.getPassword().equals(postRequestDto.getPassword())) {
            postRepository.deleteById(id);
        } else {
            throw new DeleteUnauthorizedException("비밀번호가 틀립니다.",ErrorCode.DELETE_UNAUTHORIZED);
        }
        return id;
    }

    public Boolean checkPassword(Long id, PostRequestDto postRequestDto) {
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND)).getPassword().equals(postRequestDto.getPassword());
    }
}
