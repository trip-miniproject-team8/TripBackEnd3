package com.example.tripbackend3.security.filter;

import com.example.tripbackend3.entity.User;
import com.example.tripbackend3.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {
    final private ObjectMapper objectMapper;
    final private UserRepository userRepository;

//    @Autowired
//    public FormLoginFilter(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }

    public FormLoginFilter(final AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.userRepository = userRepository;
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try {
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();
//            Optional<User> user= userRepository.findByUsername(username);
//            if(user.isPresent()){
//                throw new IllegalArgumentException("???????????? ?????? ????????? ?????????.");
//            }
            if(!userRepository.existsByUsername(username)){
                System.out.println("???????????? ?????? ?????? ????????????. ");
                
            }

            authRequest = new UsernamePasswordAuthenticationToken(username, password);

        } catch (Exception e) {
            throw new RuntimeException("username, password ????????? ???????????????. (JSON)");
        }

        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
