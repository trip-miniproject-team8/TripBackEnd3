package com.example.tripbackend3.entity;

import com.example.tripbackend3.dto.PostReceiveDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = true)
    private int commentCtn;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    //Post생성자
    public Post(PostReceiveDto postReceiveDto, User user){
        this.userNickname=postReceiveDto.getUserNickname();
        this.content=postReceiveDto.getContent();
        this.imageUrl=postReceiveDto.getImageUrl();
        this.commentCtn=postReceiveDto.getCommentCtn();
        this.user=user;
    }

    //Post 수정 메소드
    public void update(PostReceiveDto postReceiveDto, User user){
        this.userNickname=postReceiveDto.getUserNickname();
        this.content=postReceiveDto.getContent();
        this.imageUrl=postReceiveDto.getImageUrl();
        this.commentCtn=postReceiveDto.getCommentCtn();
        this.user=user;
    }
}
