package com.example.tripbackend3.service;

import com.example.tripbackend3.dto.CommentDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }



    public void createComment(Long postId, CommentDto commentDto, User user) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("null")
        );

//        List<Comment> commentList=commentRepository.findAllByPost(post);
//        post.setCommentCtn(commentList.size());
        Comment comment = new Comment(post,commentDto,user);
        commentRepository.save(comment);

    }

}