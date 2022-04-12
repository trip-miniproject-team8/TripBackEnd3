
package com.example.tripbackend3.service;

import com.example.tripbackend3.dto.request.CommentRequestDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }



    public void createComment(Long postId, CommentRequestDto requestDto,User user) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("null")
        );
        Comment comment = new Comment(post,requestDto,user);
        commentRepository.save(comment);
    }

}