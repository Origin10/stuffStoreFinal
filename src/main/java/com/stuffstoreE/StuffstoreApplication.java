package com.stuffstoreE;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stuffstoreE.domain.User;
import com.stuffstoreE.domain.security.Role;
import com.stuffstoreE.domain.security.UserRole;
import com.stuffstoreE.service.MemService;
import com.stuffstoreE.utility.SecurityUtility;

@SpringBootApplication
public class StuffstoreApplication implements CommandLineRunner {
	
	@Autowired
	private MemService memService;

	public static void main(String[] args) {
		SpringApplication.run(StuffstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		User user1 = new User();
		user1.setFirstName("John");
		user1.setLastName("Adams");
		user1.setUsername("j");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("JAdams@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1= new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		memService.createUser(user1, userRoles);
	}
}
