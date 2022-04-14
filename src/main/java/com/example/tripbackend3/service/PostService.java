package com.example.tripbackend3.service;

import com.example.tripbackend3.dto.request.ImageRequestDto;
import com.example.tripbackend3.dto.response.CommentDto;
import com.example.tripbackend3.dto.response.PostAllResponseDto;
import com.example.tripbackend3.dto.response.PostOneResponseDto;
import com.example.tripbackend3.dto.response.PostReceiveDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Setter
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    //게시물 저장/수정
    //user는 userDetailsImpl에서 로그인한 user 객체!
    @Transactional
    public void savePost(Long postId,PostReceiveDto postReceiveDto, User user){
        Optional<Post> postOptional=postRepository.findById(postId);
        if(postOptional.isPresent()){
            if(postOptional.get().getUser().getId() != user.getId()){
                throw new IllegalArgumentException("게시물 작성자만 본문작성 및 수정이 가능합니다");
            }
        }else{
            throw new IllegalArgumentException("이미지를 먼저 입력해주세요^^");
        }
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("이미지를 먼저 입력해주세요^^")
        );
        String userNickname=user.getUserNickname();
//        String imageUrl=imageRequestDto.getImageUrl();
//        if(imageUrl==null){
//            throw new NullPointerException("이미지를 첨부해주세요!");
//        }
        String content=postReceiveDto.getContent();
        if(content==null){
            throw new NullPointerException("본문 내용을 작성해주세요");
        }
        int commentCtn=postReceiveDto.getCommentCtn();



        String imageUrl= postOptional.get().getImageUrl();
        System.out.println("이미지url:"+imageUrl);

        //댓글은 코멘트 쪽에서 add
        //(댓글수 기본값 0, 댓글 null로 저장)
        postReceiveDto=new PostReceiveDto(userNickname, content, commentCtn);

        postOptional.get().updateExceptImage(postReceiveDto, user);
//        post.updateExceptImage(postReceiveDto, user);
//        System.out.println(imageUrl);
        System.out.println(userNickname);
        System.out.println(content);
        postRepository.save(post);
        postRepository.deleteAllByUserNicknameNull();
    }

    //전체 게시글 조회
    public List<PostAllResponseDto> showAllPost() {
        List<Post> postList = postRepository.findAllByUserNicknameNotNullOrderByCreatedAtDesc();
        List<PostAllResponseDto> posts=new ArrayList<>();
        for(Post post:postList){
            List<Comment> commentList=commentRepository.findAllByPost(post);
            int commentCtn=commentList.size();
            PostAllResponseDto postAllDto=new PostAllResponseDto(post.getId(), post.getUserNickname(), post.getUser().getUsername(),
                    post.getImageUrl(), post.getContent(), post.getCreatedAt(), commentCtn);
            posts.add(postAllDto);
        }
        return posts;
    }

    //게시물 상세 조회
    public PostOneResponseDto showOnePost(Long postId){
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 게시물이 없습니다.")
        );
        String userNickname=post.getUserNickname();
        String imageUrl=post.getImageUrl();
        String content=post.getContent();
        LocalDateTime createdAt=post.getCreatedAt();
        //댓글 쪽 repo에서 커스텀 필요
        //게시물 아이디에 해당하는 댓글이 다 나옴
        List<Comment> commentList=commentRepository.findAllByPost(post);
        int commentCtn=commentList.size();

        List<CommentDto> comments=new ArrayList<>();
        for(Comment comment:commentList){
            CommentDto commentDto=new CommentDto(comment.getUserNickname(),comment.getComment(),comment.getCreatedAt(),comment.getId());
            comments.add(commentDto);
        }

        PostOneResponseDto postOneResponseDto =
                new PostOneResponseDto(postId,userNickname, imageUrl, content,createdAt, commentCtn, comments);
        return postOneResponseDto;
    }

//    //게시글 수정
//    @Transactional
//    public void updatePost(Long postId, PostReceiveDto postReceiveDto,User user){
//
//        Post post= postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
//                ()-> new IllegalArgumentException("게시물 작성자만 수정할 수 있습니다.")
//        );
//
//        String userNickname=user.getUserNickname();
//        String content=postReceiveDto.getContent();
//        if(content==null){
//            throw new NullPointerException("내용을 입력해주세요!");
//        }
//        if(post.getImageUrl() ==null){
//            throw new NullPointerException("이미지를 입력해주세요");
//        }
//
//        //commentRepo에서 커스텀해줘야함
//        List<Comment> comments=commentRepository.findAllByPost(post);
//        int commentCtn=comments.size();
//        postReceiveDto=new PostReceiveDto(userNickname,content,commentCtn);
//        post.updateExceptImage(postReceiveDto, user);
//
//    }

    //게시물 삭제
    @Transactional
    public void deletePost(Long postId, User user) {
        //로그인한 유저와 해당 게시물 아이디로 조회 시 null값 반환한다면,
        //지금 삭제할려는 사람이 해당 게시물을 작성하지 않았다는 말이다.
        Post post = postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                ()-> new IllegalArgumentException("게시물 작성자만 삭제할 수 있습니다.")
        );
        postRepository.deleteById(postId);
        //게시물 내에 댓글도 삭제
        //commentRepo에서 커스텀해줘야함.
        commentRepository.deleteAllByPost(post);
    }

}
//제발 날라가지 마세요요