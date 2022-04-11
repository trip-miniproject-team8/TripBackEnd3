package com.example.tripbackend3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 메인 페이지
    @GetMapping("/")
    public String main() {

        return "index";
    }
    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {

        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 게시물 작성페이지
    @GetMapping("/page/post")
    public String post(){
        return "post";
    }

    //게시물 수정 페이지
    @GetMapping("/page/update")
    public  String update(){return "postUpdate"; }

    // 댓글 작성페이지
    @GetMapping("/page/reply")
    public String reply(){
        return "reply";
    }

    // 댓글 삭제페이지
    @GetMapping("/page/reply/delete")
    public String replyDelete(){
        return "replyDelete";
    }
}
