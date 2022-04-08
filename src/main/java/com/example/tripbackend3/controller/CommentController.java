package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.CommentDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/api/comment/{postId}")
    public Comment createComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal User user, @PathVariable Long postId){
        commentDto.setUserNickname(user.getUserNickname());
        Comment comment = new Comment(commentDto);
        return commentRepository.save(comment);
    }
    @DeleteMapping("/api/comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") long commentId){
        commentRepository.deleteById(commentId);
    }
}
