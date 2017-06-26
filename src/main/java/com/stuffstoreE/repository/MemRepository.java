package com.stuffstoreE.repository;

import org.springframework.data.repository.CrudRepository;

import com.stuffstoreE.domain.User;

public interface MemRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	User findByEmail(String email);
}
