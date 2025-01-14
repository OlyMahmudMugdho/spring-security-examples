package com.mahmud.spring_security_jwt_enhanced;

import com.mahmud.spring_security_jwt_enhanced.model.Role;
import com.mahmud.spring_security_jwt_enhanced.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurityJwtEnhancedApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		roleRepository.save(new Role("ROLE_USER"));
		roleRepository.save(new Role("ROLE_ADMIN"));
	}

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtEnhancedApplication.class, args);
	}

}
