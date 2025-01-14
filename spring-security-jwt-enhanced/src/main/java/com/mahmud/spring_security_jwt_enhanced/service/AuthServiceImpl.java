package com.mahmud.spring_security_jwt_enhanced.service;

import com.mahmud.spring_security_jwt_enhanced.dto.LoginDto;
import com.mahmud.spring_security_jwt_enhanced.dto.RegisterDto;
import com.mahmud.spring_security_jwt_enhanced.model.Role;
import com.mahmud.spring_security_jwt_enhanced.model.User;
import com.mahmud.spring_security_jwt_enhanced.repository.RoleRepository;
import com.mahmud.spring_security_jwt_enhanced.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String login(LoginDto loginDto) {

        // 01 - AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        /* 02 - SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 03 - Generate the token based on username and secret key
        String token = jwtTokenProvider.generateToken(authentication);

        // 04 - Return the token to controller
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        try {
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setEmail(registerDto.getEmail());
            user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
            userRepository.save(user);
            return "user registered successfully";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "user registration failed";
        }

    }


}