package com.stuffstoreE.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stuffstoreE.domain.UserPayment;
import com.stuffstoreE.repository.MemPaymentRepository;
import com.stuffstoreE.service.MemPaymentService;

@Service
public class MemPaymentServiceImpl implements MemPaymentService {

	@Autowired
	private MemPaymentRepository memPaymentRepository;
		
	public UserPayment findById(Long id) {
		return memPaymentRepository.findOne(id);
	}
	
	public void removeById(Long id) {
		memPaymentRepository.delete(id);
	}
} 
