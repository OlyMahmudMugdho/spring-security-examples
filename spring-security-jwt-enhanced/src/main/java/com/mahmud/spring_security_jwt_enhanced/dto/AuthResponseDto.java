package com.mahmud.spring_security_jwt_enhanced.dto;

public class AuthResponseDto {
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    private String accessToken;

}