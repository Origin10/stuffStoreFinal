package com.stuffstoreE.service;

import com.stuffstoreE.domain.UserPayment;

public interface MemPaymentService {
	UserPayment findById(Long id);
	
	void removeById(Long id);
}
