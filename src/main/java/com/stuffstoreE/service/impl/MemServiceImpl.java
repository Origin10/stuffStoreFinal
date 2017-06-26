package com.stuffstoreE.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.stuffstoreE.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stuffstoreE.domain.MemShipping;
import com.stuffstoreE.domain.security.PasswordResetToken;
import com.stuffstoreE.domain.security.UserRole;
import com.stuffstoreE.repository.PasswordResetTokenRepository;
import com.stuffstoreE.repository.RoleRepository;
import com.stuffstoreE.repository.MemPaymentRepository;
import com.stuffstoreE.repository.MemRepository;
import com.stuffstoreE.repository.MemShippingRepository;
import com.stuffstoreE.service.MemService;

@Service
public class MemServiceImpl implements MemService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MemService.class);
	
	@Autowired
	private MemRepository memRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MemPaymentRepository memPaymentRepository;
	
	@Autowired
	private MemShippingRepository memShippingRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}
	
	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}
	
	@Override
	public User findByUsername(String username) {
		return memRepository.findByUsername(username);
	}
	
	@Override
	public User findById(Long id){
		return memRepository.findOne(id);
	}
	
	@Override
	public User findByEmail (String email) {
		return memRepository.findByEmail(email);
	}
	
	@Override
	@Transactional
	public User createUser(User user, Set<UserRole> userRoles){
		User localUser = memRepository.findByUsername(user.getUsername());
		
		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}
			
			user.getUserRoles().addAll(userRoles);
			
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			user.setShoppingCart(shoppingCart);
			
			user.setMemShippingList(new ArrayList<MemShipping>());
			user.setUserPaymentList(new ArrayList<UserPayment>());
			
			localUser = memRepository.save(user);
		}
		
		return localUser;
	}
	
	@Override
	public User save(User user) {
		return memRepository.save(user);
	}
	
	@Override
	public void updateUserBilling(MemBilling memBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setMemBilling(memBilling);
		userPayment.setDefaultPayment(true);
		memBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		save(user);
	}
	
	@Override
	public void updateUserShipping(MemShipping memShipping, User user){
		memShipping.setUser(user);
		memShipping.setUserShippingDefault(true);
		user.getMemShippingList().add(memShipping);
		save(user);
	}
	
	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = (List<UserPayment>) memPaymentRepository.findAll();
		
		for (UserPayment userPayment : userPaymentList) {
			if(userPayment.getId() == userPaymentId) {
				userPayment.setDefaultPayment(true);
				memPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				memPaymentRepository.save(userPayment);
			}
		}
	}
	
	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<MemShipping> memShippingList = (List<MemShipping>) memShippingRepository.findAll();
		
		for (MemShipping memShipping : memShippingList) {
			if(memShipping.getId() == userShippingId) {
				memShipping.setUserShippingDefault(true);
				memShippingRepository.save(memShipping);
			} else {
				memShipping.setUserShippingDefault(false);
				memShippingRepository.save(memShipping);
			}
		}
	}

}
