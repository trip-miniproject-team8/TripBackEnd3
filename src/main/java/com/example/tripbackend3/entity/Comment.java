package com.example.tripbackend3.entity;

import com.example.tripbackend3.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped{
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Comment(CommentDto commentDto) {
        this.comment = commentDto.getComment();
        this.userNickname = commentDto.getUserNickname();
    }

    public Comment(Post post, CommentDto commentDto, User user){
        this.post = post;
        this.userNickname = commentDto.getUserNickname();
        this.comment = commentDto.getComment();
        this.user = user;
    }

}
