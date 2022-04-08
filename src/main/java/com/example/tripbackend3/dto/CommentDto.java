package com.example.tripbackend3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private String userNickname;
    private String comment;
    private String createdAt;

}
