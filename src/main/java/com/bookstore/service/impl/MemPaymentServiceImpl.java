package com.bookstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.UserPayment;
import com.bookstore.repository.MemPaymentRepository;
import com.bookstore.service.MemPaymentService;

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
