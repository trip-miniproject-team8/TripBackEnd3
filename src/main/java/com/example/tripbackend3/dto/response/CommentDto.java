package com.example.tripbackend3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {


    private String userNickname;
    private String comment;
    private LocalDateTime createdAt;
//    private String user;


}
