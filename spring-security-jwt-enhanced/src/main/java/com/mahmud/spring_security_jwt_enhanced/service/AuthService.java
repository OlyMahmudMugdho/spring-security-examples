package com.mahmud.spring_security_jwt_enhanced.service;

import com.mahmud.spring_security_jwt_enhanced.dto.LoginDto;
import com.mahmud.spring_security_jwt_enhanced.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
