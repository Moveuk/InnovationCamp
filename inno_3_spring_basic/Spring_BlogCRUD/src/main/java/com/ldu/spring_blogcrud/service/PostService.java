package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.common.exceptions.DeleteUnauthorizedException;
import com.ldu.spring_blogcrud.common.exceptions.EntityNotFoundException;
import com.ldu.spring_blogcrud.common.exceptions.ErrorCode;
import com.ldu.spring_blogcrud.common.exceptions.PostUnauthorizedException;
import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.dto.PostResponseDto;
import com.ldu.spring_blogcrud.entity.Post;
import com.ldu.spring_blogcrud.repository.PostRepository;
import com.ldu.spring_blogcrud.repository.ReplyRepository;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;
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
    private final ReplyRepository replyRepository;

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
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getAuthor(), postRequestDto.getPassword(), postRequestDto.getContent());
        postRepository.save(post);
        return post.getId();
    }

    // 글 수정
    @Transactional
    public Long update(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        if (!post.getPassword().equals(postRequestDto.getPassword())) {
            throw new PostUnauthorizedException("비밀번호가 틀립니다.", ErrorCode.POST_UNAUTHORIZED);
        }
        if (!userDetails.getUsername().equals(post.getAuthor())) { // 로그인한사람이랑 작성자가 다르면 exception
            throw new PostUnauthorizedException("작성자만 수정할 수 있습니다.", ErrorCode.POST_UNAUTHORIZED);
        }
        post.update(postRequestDto.getTitle(), postRequestDto.getContent());
        return post.getId();
    }

    public Long delete(Long id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        if (!post.getPassword().equals(postRequestDto.getPassword())) {  // 비밀번호가 다르면 exception
            throw new DeleteUnauthorizedException("비밀번호가 틀립니다.", ErrorCode.DELETE_UNAUTHORIZED);
        }
        if (!userDetails.getUsername().equals(post.getAuthor())) { // 로그인한사람이랑 작성자가 다르면 exception
            throw new DeleteUnauthorizedException("작성자만 삭제할 수 있습니다.", ErrorCode.DELETE_UNAUTHORIZED);
        }
        postRepository.deleteById(id);
        replyRepository.deleteAll(replyRepository.findAllByPostId(id));
        return id;
    }

    public Boolean checkPassword(Long id, PostRequestDto postRequestDto) {
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND)).getPassword().equals(postRequestDto.getPassword());
    }
}
