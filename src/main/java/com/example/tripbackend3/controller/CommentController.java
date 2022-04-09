package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.CommentDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.repository.PostRepository;
import com.example.tripbackend3.service.CommentService;
import com.example.tripbackend3.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;

    @PostMapping("/api/comment/{postId}")
    public void createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId){

        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("null")
        );

        commentDto.setUserNickname(userDetails.getUser().getUserNickname());
//        commentDto.setPostId(postId);
        Comment comment = new Comment(commentDto);
        commentService.createComment(postId,commentDto,userDetails.getUser());
    }
    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
        commentRepository.deleteById(commentId);
    }
}

// 테스트 한줄 추가