package com.stuffstoreE.service;

import com.stuffstoreE.domain.Payment;
import com.stuffstoreE.domain.UserPayment;

public interface PaymentService {
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
