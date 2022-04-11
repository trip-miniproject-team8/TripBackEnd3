package com.example.tripbackend3.entity;

import com.example.tripbackend3.dto.PostReceiveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column
    private Long id;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false, length = 500)
    private String content;

    @Column(nullable = true)
    private int commentCtn;

    @ManyToOne
    @JoinColumn(name="User_Id")
    private User user;




    public Post(PostReceiveDto postReceiveDto, User user){
        this.userNickname=postReceiveDto.getUserNickname();
        this.content=postReceiveDto.getContent();
        this.imageUrl=postReceiveDto.getImageUrl();
        this.commentCtn=postReceiveDto.getCommentCtn();
        this.user=user;
    }
}
