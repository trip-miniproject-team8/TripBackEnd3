package com.example.tripbackend3.dto.request;

import com.example.tripbackend3.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDto {

    private String comment;
    //private String userNickname;

}
