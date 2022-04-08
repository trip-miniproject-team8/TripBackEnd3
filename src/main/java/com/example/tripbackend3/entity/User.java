package com.example.tripbackend3.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userNickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String userId;

}
