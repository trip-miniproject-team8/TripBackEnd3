package com.example.tripbackend3.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.tripbackend3.dto.response.ImageResponseDto;
import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //이미지 저장 받아주는 부분
    public ImageResponseDto uploadFile(MultipartFile multipartFile, String dirName, User user) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("파일변환이 불가능합니다."));
        return upload(uploadFile, dirName, user);
    }
    //이미지 수정 받아주는 부분
    public ImageResponseDto updateFile(MultipartFile multipartFile, Long postId, String dirName, User user) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("파일변환이 불가능합니다."));
        return update(uploadFile, postId,dirName,user);
    }

    //이미지 저장--------------------------------
    public ImageResponseDto upload(File uploadFile, String filePath, User user) {
        System.out.println(uploadFile);
        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        Post post =new Post(uploadImageUrl, user);
        postRepository.save(post);
        ImageResponseDto imageResponseDto=new ImageResponseDto(uploadImageUrl,post.getId());
//        imageRepository.post.getId();

//        ImageRequestDto imageRequestDto=new ImageRequestDto(uploadImageUrl);
//        Image image=new Image(imageRequestDto);
//        imageRepository.save(image);
//        System.out.println("이미지 아이디 생겼나?"+image.getId());

//        ImageResponseDto imageResponseDto= new ImageResponseDto(uploadImageUrl, image.getId());
//        Post post=new Post(image.getImageUrl());
//        postRepository.save(post);

        removeNewFile(uploadFile);
        return imageResponseDto;
    }

    //이미지 수정(putmapping)----------------------
    @Transactional
    public ImageResponseDto update(File uploadFile, Long postId, String filePath, User user) {
//        Optional<Post> postOptional=postRepository.findById(postId);
//        if(postOptional.isPresent()){
//            if(postOptional.get().getUser().getId() != user.getId()){
//                throw new IllegalArgumentException("게시물 작성자만 본문작성 및 수정이 가능합니다");
//            }
//        }else{
//            throw new IllegalArgumentException("이미지를 먼저 입력해주세요^^");
//        }

        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드

        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시물 아이디가 없습니다")
        );
        post.update(uploadImageUrl);
        postRepository.save(post);

//        Post post=postRepository.findByImageId(imageId).p
//        imageRepository.save(image);
        ImageResponseDto imageResponseDto= new ImageResponseDto(uploadImageUrl, post.getId());
//        Post post=new Post(image.getImageUrl());
//        postRepository.save(post);
        removeNewFile(uploadFile);
        return imageResponseDto;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}