package com.example.tripbackend3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostAllResponseDto {

    private Long id;
    private String userNickname;
    private String username;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
    private int commentCtn;

}
