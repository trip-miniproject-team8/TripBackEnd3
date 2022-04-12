package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.PostAllResponseDto;
import com.example.tripbackend3.dto.PostOneResponseDto;
import com.example.tripbackend3.dto.PostReceiveDto;
import com.example.tripbackend3.security.UserDetailsImpl;
import com.example.tripbackend3.service.CommentService;
import com.example.tripbackend3.service.PostService;
import com.example.tripbackend3.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;

    //게시글 저장
    @PostMapping("/api/post")
    public void savePost(@RequestBody PostReceiveDto postReceiveDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.getUser());
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.savePost(postReceiveDto,userDetails.getUser());


    }
    //게시글 전체 조회
    @GetMapping("/api/post")
    public List<PostAllResponseDto> showAllPost(){
        List<PostAllResponseDto> posts = postService.showAllPost();
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
    public void updatePost(@PathVariable Long postId, @RequestBody PostReceiveDto postReceiveDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.updatePost(postId, postReceiveDto, userDetails.getUser());

    }

    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.deletePost(postId, userDetails.getUser());
    }

    //    이미지 업로드
    @PostMapping("/api/image")
    public ResponseEntity<String> updateUserImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        System.out.println(multipartFile);
        String image = s3Uploader.uploadFile(multipartFile, "static");
        return ResponseEntity
                .ok()
                .body(image);

    }

}