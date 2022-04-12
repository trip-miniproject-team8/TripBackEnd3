package com.example.tripbackend3.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final String DEFAULT_FAILURE_URL = "/user/login";

    @Autowired
    public FormLoginFailHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
//       String errorMsg = null;

        if (exception instanceof AuthenticationServiceException) {
            request.setAttribute("loginFailMsg", "존재하지 않는 사용자입니다.");
        }else if(exception instanceof BadCredentialsException) {
            request.setAttribute("loginFailMsg", "아이디 또는 비밀번호가 틀립니다.");
        }

        request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request,response);
    }

}
