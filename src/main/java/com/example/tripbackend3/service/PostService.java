package com.example.tripbackend3.service;

import com.example.tripbackend3.dto.CommentDto;
import com.example.tripbackend3.dto.PostAllResponseDto;
import com.example.tripbackend3.dto.PostOneResponseDto;
import com.example.tripbackend3.dto.PostReceiveDto;
import com.example.tripbackend3.entity.Comment;
import com.example.tripbackend3.entity.Post;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.CommentRepository;
import com.example.tripbackend3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //게시물 저장
    //user는 userDetailsImpl에서 로그인한 user 객체!
    public void savePost(PostReceiveDto postReceiveDto, User user){

        String userNickname=user.getUserNickname();
        String imageUrl=postReceiveDto.getImageUrl();
        String content=postReceiveDto.getContent();
        int commentCtn=postReceiveDto.getCommentCtn();
        //댓글은 코멘트 쪽에서 add
        //아래 생성자에를 통해 정보 주입(댓글수 기본값 0, 댓글 null로 저장)
        postReceiveDto=new PostReceiveDto(userNickname, content, imageUrl, commentCtn);
        Post post=new Post(postReceiveDto,user);
        postRepository.save(post);
    }

    //전체 게시글 조회
    public List<PostAllResponseDto> showAllPost() {
        List<Post> postList = postRepository.findAll();
        List<PostAllResponseDto> posts=new ArrayList<>();
        for(Post post:postList){
            List<Comment> commentList=commentRepository.findAllByPost(post);
            int commentCtn=commentList.size();
            PostAllResponseDto postAllDto=new PostAllResponseDto(post.getId(), post.getUserNickname(),
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
        //댓글 수
        int commentCtn=commentList.size();
        //출력형태를 맞춰주기 위해 commentDto로 변환
        List<CommentDto> comments=new ArrayList<>();
        for(Comment comment:commentList){
            CommentDto commentDto=new CommentDto(comment.getUserNickname(),comment.getComment(),comment.getCreatedAt());
            comments.add(commentDto);
        }

        PostOneResponseDto postOneResponseDto =
                new PostOneResponseDto(postId,userNickname, imageUrl, content,createdAt, commentCtn, comments);
        return postOneResponseDto;
    }

    //게시글 수정
    @Transactional
    public void updatePost(Long postId, PostReceiveDto postReceiveDto, User user){

        Post post= postRepository.findByIdAndUserId(postId,user.getId()).orElseThrow(
                ()-> new IllegalArgumentException("게시물 작성자만 수정할 수 있습니다.")
        );

        //comment와 imageUrl만 프론트에서 줌
        String userNickname=user.getUserNickname();
        String content=postReceiveDto.getContent();
        String imageUrl=postReceiveDto.getImageUrl();
        //commentRepo에서 커스텀해줘야함
        List<Comment> comments=commentRepository.findAllByPost(post);
        int commentCtn=comments.size();
        postReceiveDto=new PostReceiveDto(userNickname,content,imageUrl,commentCtn);
        post.update(postReceiveDto, user);
    }

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