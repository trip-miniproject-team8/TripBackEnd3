package com.example.tripbackend3.dto;

import java.util.Collection;

public class AuthenticationToken {

    private String username;
    private Collection authorities;
    private String jsessionid;

    public AuthenticationToken(String username, Collection collection, String token) {
        this.username = username;
        this.authorities = collection;
        this.jsessionid = token;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Collection getAuthorities() {
        return authorities;
    }
    public void setAuthorities(Collection authorities) {
        this.authorities = authorities;
    }
    public String getJsessionid() {
        return jsessionid;
    }
    public void setToken(String token) {
        this.jsessionid = token;
    }
}
