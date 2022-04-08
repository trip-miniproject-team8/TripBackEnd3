package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.CommentDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
