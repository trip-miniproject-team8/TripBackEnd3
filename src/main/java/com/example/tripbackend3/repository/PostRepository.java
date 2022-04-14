package com.example.tripbackend3.repository;


import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUserId(Long id, Long userId);

    void deleteAllByUserNicknameNull();
    //최신순 정렬
    List<Post> findAllByUserNicknameNotNullOrderByCreatedAtDesc();

}