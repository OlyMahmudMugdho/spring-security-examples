package com.mahmud.spring_security_jwt_enhanced.repository;

import com.mahmud.spring_security_jwt_enhanced.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}

