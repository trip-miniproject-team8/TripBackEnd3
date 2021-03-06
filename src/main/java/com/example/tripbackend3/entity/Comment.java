package com.example.tripbackend3.entity;

import com.example.tripbackend3.dto.request.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class Comment extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name="POST_ID")
    private Post post;

    //private Long post_id;

    @Column(nullable = false)
    private String userNickname;

    @Column(nullable = false)
    private String comment;

    public Comment(Post post, CommentRequestDto requestDto, User user) {
        this.post = post;
        this.userNickname = user.getUserNickname();
        this.comment = requestDto.getComment();
        this.user = user;
    }


    public Comment(Post post, CommentRequestDto requestDto, String userNickname) {
        this.post = post;
        this.userNickname = userNickname;
        this.comment = requestDto.getComment();
    }

    public Comment(Post post, CommentRequestDto requestDto, String userNickname, User user) {
        this.post = post;
        this.userNickname = userNickname;
        this.comment = requestDto.getComment();
//        this.user = user;
    }
}
