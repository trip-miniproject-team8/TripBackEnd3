package com.example.tripbackend3.controller;

import com.example.tripbackend3.dto.*;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.security.UserDetailsImpl;
import com.example.tripbackend3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collections;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입 (2022.04.11 api 설계서 )
    @PostMapping("/api/signup")
    public void registerUser(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
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
            throw new IllegalArgumentException("로그인 한 ");
        } else {
            loginDto.setUsername(userDetails.getUsername());
            return loginDto;
        }
    }


}