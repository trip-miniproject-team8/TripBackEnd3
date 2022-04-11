package com.example.tripbackend3.repository;

import com.example.tripbackend3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    boolean existsByUserNickname(String userNickname);
    boolean existsByUsername(String username);

}
