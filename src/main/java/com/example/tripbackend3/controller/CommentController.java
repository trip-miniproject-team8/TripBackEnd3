package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.CommentRequestDto;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.repository.PostRepository;
import com.example.tripbackend3.security.UserDetailsImpl;
import com.example.tripbackend3.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;


    @PostMapping("/api/comment/{postId}")
    public void createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId){
//        System.out.println(userDetails.getUsername());
//        postRepository.findById(postId).orElseThrow(
//                () -> new IllegalArgumentException("null")
//        );
//        requestDto.setUserNickname(userDetails.getUserNickName());
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        commentService.createComment(postId,requestDto,userDetails.getUser());
    }
    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
        commentRepository.deleteById(commentId);
    }
}

// 테스트 한줄 추가