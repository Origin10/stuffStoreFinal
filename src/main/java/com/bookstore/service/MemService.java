package com.bookstore.service;

import java.util.Set;

import com.bookstore.domain.MemShipping;
import com.bookstore.domain.User;
import com.bookstore.domain.MemBilling;
import com.bookstore.domain.UserPayment;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;

public interface MemService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail (String email);
	
	User findById(Long id);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	void updateUserBilling(MemBilling memBilling, UserPayment userPayment, User user);
	
	void updateUserShipping(MemShipping memShipping, User user);
	
	void setUserDefaultPayment(Long userPaymentId, User user);
	
	void setUserDefaultShipping(Long userShippingId, User user);
}
