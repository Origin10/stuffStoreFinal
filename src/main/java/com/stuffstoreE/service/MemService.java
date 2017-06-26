package com.stuffstoreE.service;

import java.util.Set;

import com.stuffstoreE.domain.MemShipping;
import com.stuffstoreE.domain.User;
import com.stuffstoreE.domain.MemBilling;
import com.stuffstoreE.domain.UserPayment;
import com.stuffstoreE.domain.security.PasswordResetToken;
import com.stuffstoreE.domain.security.UserRole;

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
