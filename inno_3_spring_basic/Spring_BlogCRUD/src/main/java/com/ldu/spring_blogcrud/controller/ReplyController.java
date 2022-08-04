package com.ldu.spring_blogcrud.controller;

import com.ldu.spring_blogcrud.dto.PostRequestDto;
import com.ldu.spring_blogcrud.dto.PostResponseDto;
import com.ldu.spring_blogcrud.dto.ReplyRequestDto;
import com.ldu.spring_blogcrud.dto.ReplyResponseDto;
import com.ldu.spring_blogcrud.security.UserDetailsImpl;
import com.ldu.spring_blogcrud.security.provider.JwtProvider;
import com.ldu.spring_blogcrud.service.PostService;
import com.ldu.spring_blogcrud.service.ReplyService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(value = "댓글 컨트롤러")
@RequiredArgsConstructor
@RestController
public class ReplyController {

    private final ReplyService replyService;
    private final JwtProvider jwtProvider;

    @ApiResponses({
            @ApiResponse(code=200, message="댓글 조회 완료"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @GetMapping(path = "/api/replies/{id}")
    @ApiOperation(value = "댓글 조회", notes = "게시글에 대한 댓글을 조회한다.")
    @ApiImplicitParam(name = "id", value = "Reply Entity Id값")
    public List<ReplyResponseDto> getReplyList(@PathVariable(value = "id") Long postId) {
        return replyService.getRepliesList(postId);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="댓글 작성 완료"),
            @ApiResponse(code=500, message="서버 에러")
    })
    @PostMapping(path = "/api/replies/{postId}")
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    @ApiImplicitParam(name = "postId", value = "Post Entity Id값")
    public Long createReply(@PathVariable Long postId, @Valid @RequestBody ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        String accesstoken = request.getHeader("Access-Token");
        UserDetailsImpl userDetails = (UserDetailsImpl) jwtProvider.getAuthentication(accesstoken).getPrincipal();
        replyRequestDto.setPostId(postId);
        replyRequestDto.setAuthor(userDetails.getUsername());
        return replyService.create(replyRequestDto);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="댓글 수정 완료"),
            @ApiResponse(code=403, message="수정 권한 없음."),
            @ApiResponse(code=500, message="서버 에러")
    })
    @PutMapping(path = "/api/replies/{id}")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    @ApiImplicitParam(name = "id", value = "Reply Entity Id값")
    public Long updatePost(@PathVariable Long id, @RequestBody @ApiIgnore ReplyRequestDto replyRequestDto, HttpServletRequest request) {
        String accesstoken = request.getHeader("Access-Token");
        UserDetailsImpl userDetails = (UserDetailsImpl) jwtProvider.getAuthentication(accesstoken).getPrincipal();
        return replyService.update(id, replyRequestDto, userDetails);
    }

    @ApiResponses({
            @ApiResponse(code=200, message="댓글 삭제 완료"),
            @ApiResponse(code=403, message="삭제 권한 없음."),
            @ApiResponse(code=500, message="서버 에러")
    })
    @DeleteMapping(path = "/api/replies/{id}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    @ApiImplicitParam(name = "id", value = "Reply Entity Id값")
    public Long deletePost(@PathVariable Long id, HttpServletRequest request) {
        String accesstoken = request.getHeader("Access-Token");
        UserDetailsImpl userDetails = (UserDetailsImpl) jwtProvider.getAuthentication(accesstoken).getPrincipal();
        return replyService.delete(id, userDetails);
    }
}
