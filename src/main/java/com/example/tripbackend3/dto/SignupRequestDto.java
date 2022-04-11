package com.example.tripbackend3.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Setter
@Getter
@Data
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9-_]{3,10}$")
    private String username;

    @NotBlank
    //@Pattern(regexp = "^[a-z0-9-_]{4,10}$")
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String userNickname;


}