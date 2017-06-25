package com.bookstore.service;

import com.bookstore.domain.MemShipping;

public interface MemShippingService {
	MemShipping findById(Long id);
	
	void removeById(Long id);
}
