package com.example.tripbackend3.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class PostReceiveDto {

    private String userNickname;
    private String content;
    private int commentCtn;

    public PostReceiveDto(String userNickname, String content, int commentCtn){
        this.userNickname=userNickname;
        this.content=content;
        this.commentCtn=commentCtn; //기본값 0

    }

}
