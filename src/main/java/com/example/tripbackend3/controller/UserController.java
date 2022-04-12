package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.request.LoginDto;
import com.example.tripbackend3.dto.request.SignupRequestDto;
import com.example.tripbackend3.dto.response.IdCheckDto;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.security.UserDetailsImpl;
import com.example.tripbackend3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    //회원가입 (2022.04.11 api 설계서 )
//    @PostMapping("/api/signup")
//    public void registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
//        userService.registerUser(requestDto);
//    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    public ResponseEntity<User> registerUser(@RequestBody SignupRequestDto requestDto) {

        User user = userService.registerUser(requestDto);
        return ResponseEntity.ok(user);
    }


    //아이디 중복 검사
    @PostMapping("/api/idCheck")
    public IdCheckDto vaildId(@RequestBody LoginDto requestDto) {

        return userService.vaildId(requestDto);
    }

    //로그인 여부 확인.
    @PostMapping("/api/islogin")
    public LoginDto checkLogin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        LoginDto loginDto = new LoginDto();

        if (userDetails == null) {
            throw new NullPointerException("로그인 한 유저가 아닙니다. ");
        } else {
            loginDto.setUsername(userDetails.getUsername());
            return loginDto;
        }
    }

}