package com.bookstore.service;

import com.bookstore.domain.UserPayment;

public interface MemPaymentService {
	UserPayment findById(Long id);
	
	void removeById(Long id);
}
