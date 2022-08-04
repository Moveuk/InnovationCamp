package com.ldu.spring_blogcrud.service;

import com.ldu.spring_blogcrud.common.exceptions.DeleteUnauthorizedException;
import com.ldu.spring_blogcrud.common.exceptions.EntityNotFoundException;
import com.ldu.spring_blogcrud.common.exceptions.ErrorCode;
import com.ldu.spring_blogcrud.common.exceptions.PostUnauthorizedException;
import com.ldu.spring_blogcrud.dto.ReplyRequestDto;
import com.ldu.spring_blogcrud.dto.ReplyResponseDto;
import com.ldu.spring_blogcrud.entity.Reply;
import com.ldu.spring_blogcrud.repository.ReplyRepository;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    // 댓글 목록 리스트 받아오기
    public List<ReplyResponseDto> getRepliesList(Long postId) {
        List<Reply> replies = replyRepository.findAllByPostIdOrderByCreatedAtDesc(postId);
        List<ReplyResponseDto> repliesList = new ArrayList<>();
        replies.stream().forEach(reply -> repliesList.add(new ReplyResponseDto(reply)));
        return repliesList;
    }

    // 글 등록
    @Transactional
    public Long create(ReplyRequestDto replyRequestDto) {
        Reply reply = new Reply(replyRequestDto);
        replyRepository.save(reply);
        return reply.getId();
    }

    // 글 수정
    @Transactional
    public Long update(Long id, ReplyRequestDto replyRequestDto, UserDetailsImpl userDetails) {
        Reply reply = replyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("댓글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        if (!userDetails.getUsername().equals(reply.getAuthor())) { // 로그인한사람이랑 작성자가 다르면 exception
            throw new PostUnauthorizedException("작성자만 수정할 수 있습니다.", ErrorCode.POST_UNAUTHORIZED);
        }
        reply.update(replyRequestDto);
        return reply.getId();
    }

    public Long delete(Long id, ReplyRequestDto replyRequestDto, UserDetailsImpl userDetails) {
        Reply reply = replyRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("댓글이 존재하지 않습니다.", ErrorCode.ENTITY_NOT_FOUND));
        if (!userDetails.getUsername().equals(reply.getAuthor())) { // 로그인한사람이랑 작성자가 다르면 exception
            throw new DeleteUnauthorizedException("작성자만 삭제할 수 있습니다.", ErrorCode.DELETE_UNAUTHORIZED);
        }
        replyRepository.deleteById(id);
        return id;
    }
}
