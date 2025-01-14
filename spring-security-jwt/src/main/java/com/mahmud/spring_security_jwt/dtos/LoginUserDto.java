package com.mahmud.spring_security_jwt.dtos;

import com.mahmud.spring_security_jwt.entities.User;
public class LoginUserDto {
    private String email;
    private String password;

    public LoginUserDto(User user) {}

    public LoginUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}