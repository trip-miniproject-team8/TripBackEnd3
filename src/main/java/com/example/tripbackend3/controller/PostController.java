package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.PostAllResponseDto;
import com.example.tripbackend3.dto.PostOneResponseDto;
import com.example.tripbackend3.dto.PostReceiveDto;
import com.example.tripbackend3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    //게시글 저장
    @PostMapping("/api/post")
    public void savePost(@RequestBody PostReceiveDto postReceiveDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.savePost(postReceiveDto,userDetails.getUser());

    }
    //게시글 전체 조회
    @GetMapping("/api/post")
    public List<PostAllResponseDto> showAllPost(){
        List<PostAllResponseDto> posts=postService.showAllPost();
        return posts;
    }

    //게시글 상세 조회
    @GetMapping("/api/post/{postId}")
    public PostOneResponseDto showOnePost(@PathVariable Long postId){
        PostOneResponseDto postOneResponseDto=postService.showOnePost(postId);
        return postOneResponseDto;
    }

    //게시글 수정
    @PutMapping("/api/post/{postId}")
    public void updatePost(@PathVariable Long id, @RequestBody PostReceiveDto postReceiveDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.updatePost(id, postReceiveDto, userDetails.getUser());

    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.deletePost(id, userDetails.getUser());
    }
}
