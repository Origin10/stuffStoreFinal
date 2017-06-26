package com.stuffstoreE.service.impl;

import com.stuffstoreE.domain.MemShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stuffstoreE.repository.MemShippingRepository;
import com.stuffstoreE.service.MemShippingService;

@Service
public class MemShippingServiceImpl implements MemShippingService {
	
	@Autowired
	private MemShippingRepository memShippingRepository;
	
	
	public MemShipping findById(Long id) {
		return memShippingRepository.findOne(id);
	}
	
	public void removeById(Long id) {
		memShippingRepository.delete(id);
	}

}
