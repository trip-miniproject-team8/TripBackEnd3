package com.example.tripbackend3.dto;

import com.example.tripbackend3.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentRequestDto {

    private User user;
    private String userNickname;
    private String comment;
}
