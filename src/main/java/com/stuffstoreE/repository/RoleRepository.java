package com.stuffstoreE.repository;

import org.springframework.data.repository.CrudRepository;

import com.stuffstoreE.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
}
