package com.stuffstoreE.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stuffstoreE.domain.User;
import com.stuffstoreE.repository.MemRepository;

@Service
public class UserSecurityService implements UserDetailsService{
	
	@Autowired
	private MemRepository memRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = memRepository.findByUsername(username);
		
		if(null == user) {
			throw new UsernameNotFoundException("使用者帳號未找到");
		}
		
		return user;
	}

}
