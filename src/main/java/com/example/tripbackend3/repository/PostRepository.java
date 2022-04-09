package com.example.tripbackend3.repository;


import com.example.tripbackend3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUserId(Long id, Long userId);
//    boolean existsByIdAndUser_Id(Long id, Long userId);
}
