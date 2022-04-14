package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.request.CommentRequestDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.security.UserDetailsImpl;
import com.example.tripbackend3.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @PostMapping("/api/comment/{postId}")
    public void createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId){

        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        if(requestDto.getComment().length()<1){
            throw new IllegalArgumentException("댓글을 입력해주세요");
        }
        commentService.createComment(postId,requestDto,userDetails.getUser());
    }
    @Transactional
    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        Long userId=userDetails.getUser().getId();
        List<Comment> deleteList=commentRepository.deleteByIdAndUserId(commentId, userId);
        System.out.println(deleteList.size());

        if(deleteList.size()==0){
            throw new IllegalArgumentException("댓글 작성자만 삭제가 가능합니다.");
        }
        //        commentRepository.deleteById(commentId);
    }
}
// 테스트 한줄 추가