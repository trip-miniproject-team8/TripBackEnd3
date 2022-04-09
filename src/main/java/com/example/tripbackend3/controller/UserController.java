package com.example.tripbackend3.controller;



import com.example.tripbackend3.dto.IdCheckDto;
import com.example.tripbackend3.dto.IdCheckRequestDto;
import com.example.tripbackend3.dto.LoginDto;
import com.example.tripbackend3.dto.SignupRequestDto;
import com.example.tripbackend3.service.UserDetailsImpl;
import com.example.tripbackend3.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;




@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/api/signup")
    public void registerUser(@Valid @RequestBody SignupRequestDto requestDto){
        userService.registerUser(requestDto);
    }

    //아이디 중복 검사
    @PostMapping("/api/idCheck")
    public IdCheckDto vaildId(@RequestBody IdCheckRequestDto requestDto){

        return userService.vaildId(requestDto);
    }

    //로그인 여부 확인.
//    @GetMapping("/api/islogin")
//    public LoginDto checkLogin(UserDetailsImpl userDetails){
//        LoginDto loginDto = new LoginDto();
//
//        if (userDetails ==  null) {
//            throw new IllegalArgumentException();
//        } else {
//            loginDto.setUsername(userDetails.getUsername());
//            return loginDto;
//        }
//    }


}