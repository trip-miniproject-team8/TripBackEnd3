package com.example.tripbackend3.repository;

import com.example.tripbackend3.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
