package com.example.tripbackend3.service;



import com.example.tripbackend3.dto.request.LoginDto;
import com.example.tripbackend3.dto.request.SignupRequestDto;
import com.example.tripbackend3.dto.response.IdCheckDto;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service

public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입 수정 .
    @Transactional
    public User registerUser(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String userNickname = requestDto.getUserNickname();
        // 중복 로그인 확인
        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다!");
        }
        if (userRepository.existsByUserNickname(userNickname)){
            throw new IllegalArgumentException("이미 사용중인 닉네임 입니다!");
        }
        if(!username.matches("^[a-z0-9-_]{3,10}$")){
            throw new IllegalArgumentException("아이디는 영어와 숫자로 3~9자리로 입력하셔야 합니다!");
        }
        if(!requestDto.getPassword().matches("^[a-z0-9-_]{4,10}$")){
            throw new IllegalArgumentException("비빌번호는 영어와 숫자로 4~12 자리로 입력하셔야 합니다!");
        }




        User user = new User(username, password,userNickname);
       return userRepository.save(user);
    }

    //검증 데이터 메세지.
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    //아이디 중복검사
    public IdCheckDto vaildId(LoginDto requestDto) {
        String username = requestDto.getUsername();
        IdCheckDto idCheckDto = new IdCheckDto();
        idCheckDto.setResult(!userRepository.existsByUsername(username));

      return idCheckDto;
    }

}