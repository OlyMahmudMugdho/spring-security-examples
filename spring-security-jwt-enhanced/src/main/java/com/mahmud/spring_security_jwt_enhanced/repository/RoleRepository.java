package com.mahmud.spring_security_jwt_enhanced.repository;

import com.mahmud.spring_security_jwt_enhanced.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
