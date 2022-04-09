package com.example.tripbackend3.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
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
