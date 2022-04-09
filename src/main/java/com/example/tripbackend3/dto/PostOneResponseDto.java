package com.example.tripbackend3.dto;

import com.example.tripbackend3.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostOneResponseDto {

    private Long postId;
    private String userNickname;
    private String imageUrl;
    private String content;
    private LocalDateTime createdAt;
    private int commentCtn;
    private List<Comment> comments= new ArrayList<>();

}
