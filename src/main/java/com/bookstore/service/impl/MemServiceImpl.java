package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bookstore.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.MemShipping;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.PasswordResetTokenRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.MemPaymentRepository;
import com.bookstore.repository.MemRepository;
import com.bookstore.repository.MemShippingRepository;
import com.bookstore.service.MemService;

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
