package com.stuffstoreE.service.impl;

import org.springframework.stereotype.Service;

import com.stuffstoreE.domain.Payment;
import com.stuffstoreE.domain.UserPayment;
import com.stuffstoreE.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());
		
		return payment;
	}

}
