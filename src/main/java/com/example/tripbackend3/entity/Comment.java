package com.example.tripbackend3.entity;

import javax.persistence.*;

public class Comment extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String comment;
}
