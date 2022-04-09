package com.example.tripbackend3.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class PostReceiveDto {

    private String userNickname;
    private String content;
    private String imageUrl;
    private int commentCtn;

    public PostReceiveDto(String userNickname, String content, String imageUrl, int commentCtn){
        this.userNickname=userNickname;
        this.content=content;
        this.imageUrl=imageUrl;
        this.commentCtn=commentCtn; //기본값 0

    }

}
