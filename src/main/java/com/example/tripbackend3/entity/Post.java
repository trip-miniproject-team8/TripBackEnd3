package com.example.tripbackend3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "POST_ID")
    private Long Id;

//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = false)
    private Long commentCtn;

    //@OrderBy("id desc")
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "COMMENT_ID")
    private List<Comment> commentList;
}
