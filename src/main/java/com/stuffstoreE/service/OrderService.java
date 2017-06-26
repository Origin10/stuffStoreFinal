package com.stuffstoreE.service;

import com.stuffstoreE.domain.BillingAddress;
import com.stuffstoreE.domain.Order;
import com.stuffstoreE.domain.Payment;
import com.stuffstoreE.domain.ShippingAddress;
import com.stuffstoreE.domain.ShoppingCart;
import com.stuffstoreE.domain.User;

public interface OrderService {
	Order createOrder(ShoppingCart shoppingCart,
			ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment,
			String shippingMethod,
			User user);
	
	Order findOne(Long id);
}
