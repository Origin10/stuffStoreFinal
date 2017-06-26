package com.stuffstoreE.service;

import com.stuffstoreE.domain.MemShipping;

public interface MemShippingService {
	MemShipping findById(Long id);
	
	void removeById(Long id);
}
