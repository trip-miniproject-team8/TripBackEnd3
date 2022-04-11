package com.example.tripbackend3.service;



import com.example.tripbackend3.dto.*;
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

    public User login(LoginDto loginDto){
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                IllegalArgumentException::new);
       return user;
    }


   //회원가입 (2022.04.11 api 설계서 )
    public void registerUser(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String userNickname = requestDto.getUserNickname();

        User user = new User(username, password,userNickname);
        userRepository.save(user);
    }

    //회원가입 유호성 검사와 검사에 따른 메세지도 같이 보내기 .
//    public SignupResponseDto registerUser(SignupRequestDto requestDto) {
//
//        String username = requestDto.getUsername();
//        String userNickname = requestDto.getUserNickname();
//        String password = passwordEncoder.encode(requestDto.getPassword());
//        //중복 로그인 체크 .
////        Optional<User> found = userRepository.findByUsername(username);
////        if(found.isPresent())
////            throw new IllegalArgumentException("사용중인 아이디 입니다.");
//        if(userRepository.existsByUsername(username)){
//            throw new IllegalArgumentException("사용중인 아이디 입니다.");
//        }
//        if(userRepository.existsByUserNickname(userNickname)){
//            throw new IllegalArgumentException("사용중인 닉네임 입니다.");
//        }
//
//        User user = new User(username, password,userNickname);
//        userRepository.save(user);
//        return new SignupResponseDto();
//    }

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


    public User readUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new IllegalArgumentException("")
        );
        return user;
    }

}