package com.example.tripbackend3.entity;

import com.example.tripbackend3.dto.request.ImageRequestDto;
import com.example.tripbackend3.dto.response.PostReceiveDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@Setter
@Getter
@Entity
public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    @Column
    private String userNickname;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = true, length = 500)
    private String content;

    @Column(nullable = true)
    private int commentCtn;

    //여기도 nullable true?
    @ManyToOne
    @JoinColumn(name="User_Id")
    private User user;


    public Post(String imageUrl, User user){
        this.imageUrl=imageUrl;
        this.user = user;
    }

    public void updateExceptImage(PostReceiveDto postReceiveDto,User user){
        this.userNickname=postReceiveDto.getUserNickname();
        this.content=postReceiveDto.getContent();
        this.commentCtn=postReceiveDto.getCommentCtn();
        this.user=user;
    }
//    public Post(PostReceiveDto postReceiveDto, ImageRequestDto imageRequestDto, User user){
//        this.userNickname=postReceiveDto.getUserNickname();
//        this.content=postReceiveDto.getContent();
//        this.imageUrl=imageRequestDto.getImageUrl();
//        this.commentCtn=postReceiveDto.getCommentCtn();
//        this.user=user;
//    }
//    //----------이미지 업로드 수정반영------------
//    public Post(PostReceiveDto postReceiveDto, String imageUrl, User user, Long imageId){
//        this.userNickname=postReceiveDto.getUserNickname();
//        this.content=postReceiveDto.getContent();
//        this.imageUrl=imageUrl;
//        this.commentCtn=postReceiveDto.getCommentCtn();
//        this.user=user;
//
//    }

//    public void update(PostReceiveDto postReceiveDto, ImageRequestDto imageRequestDto,User user){
//        this.userNickname=postReceiveDto.getUserNickname();
//        this.content=postReceiveDto.getContent();
//        this.imageUrl= imageRequestDto.getImageUrl();
//        this.commentCtn=postReceiveDto.getCommentCtn();
//        this.user=user;
//    }

    public void update(String imageUrl){
        this.imageUrl=imageUrl;

    }
}