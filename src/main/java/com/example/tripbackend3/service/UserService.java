package com.example.tripbackend3.service;



import com.example.tripbackend3.dto.IdCheckDto;
import com.example.tripbackend3.dto.IdCheckRequestDto;
import com.example.tripbackend3.dto.SignupRequestDto;
import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
//@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입
    public void registerUser(SignupRequestDto requestDto) {
        
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String userNickname = requestDto.getUserNickname();

        User user = new User(username, password,userNickname);
        userRepository.save(user);
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
    public IdCheckDto vaildId(IdCheckRequestDto requestDto) {
        String username = requestDto.getUsername();
        IdCheckDto idCheckDto = new IdCheckDto();
        idCheckDto.setResult( userRepository.existsByUsername(username));

      return idCheckDto;
    }


}