package com.example.tripbackend3.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
//    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String userNickname;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    public User(String username, String password, String userNickname) {
        this.username = username;
        this.password = password;
        this.userNickname = userNickname;
    }



//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
}
