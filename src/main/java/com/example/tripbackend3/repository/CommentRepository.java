package com.example.tripbackend3.repository;

import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
//    List<Comment>  findAllByPostId(Long postId);
    //void deleteAllByPostId(Long postId);

    List<Comment> deleteAllByPost(Post post);
    List<Comment> deleteByIdAndUserId(Long commentId, Long userId);
    List<Comment> findAllByPost(Post post);
}
