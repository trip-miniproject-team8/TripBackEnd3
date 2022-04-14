package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.request.ImageRequestDto;
import com.example.tripbackend3.dto.response.ImageResponseDto;
import com.example.tripbackend3.dto.response.PostAllResponseDto;
import com.example.tripbackend3.dto.response.PostOneResponseDto;
import com.example.tripbackend3.dto.response.PostReceiveDto;
import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.repository.PostRepository;
import com.example.tripbackend3.security.UserDetailsImpl;
import com.example.tripbackend3.service.CommentService;
import com.example.tripbackend3.service.PostService;
import com.example.tripbackend3.service.S3Uploader;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;
//    private String imageUrl;

    //게시글 저장
    @PutMapping("/api/post/{postId}")
    public void savePost(@PathVariable Long postId, @RequestBody PostReceiveDto postReceiveDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        System.out.println("유저정보안나와"+userDetails.getUsername());
        System.out.println(userDetails.getUser());
        //토큰 방식 전환에 따른 필요 유무 생각
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.savePost(postId,postReceiveDto, userDetails.getUser());
//        imageUrl="";
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

//    //게시글 수정
//    @PutMapping("/api/post-up/{postId}")
//    public void updatePost(@PathVariable("postId") Long postId, @RequestBody PostReceiveDto postReceiveDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//
//        if(userDetails.getUser()==null){
//            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
//        }
//
//        postService.updatePost(postId, postReceiveDto,userDetails.getUser());
//
//    }


    //게시글 삭제
    @DeleteMapping("/api/post/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(userDetails.getUser()==null){
            throw new IllegalArgumentException("로그인을 먼저 진행해주세요");
        }
        postService.deletePost(postId, userDetails.getUser());
    }

    //이미지 업로드 저장
    @PostMapping("/api/image")
    public ImageResponseDto uploadImage(@RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        ImageResponseDto imageResponseDto = s3Uploader.uploadFile(multipartFile, "static", userDetails.getUser());


        System.out.println("여기서 오류?");
        return imageResponseDto;
    }

    //이미지 업로드 수정
    @PutMapping("/api/image/{postId}")
    public ImageResponseDto updateImage(@PathVariable Long postId,@RequestParam("file") MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        Optional<Post> postOptional=postRepository.findById(postId);
        if(postOptional.isPresent()){
            if(postOptional.get().getUser().getId() != userDetails.getUser().getId()){
                throw new IllegalArgumentException("게시물 작성자만 본문작성 및 수정이 가능합니다");
            }
        }else{
            throw new IllegalArgumentException("이미지를 먼저 입력해주세요^^");
        }

        ImageResponseDto imageResponseDto = s3Uploader.updateFile(multipartFile, postId,"static",userDetails.getUser());

        System.out.println("여기서 오류?");
        return imageResponseDto;
    }
}