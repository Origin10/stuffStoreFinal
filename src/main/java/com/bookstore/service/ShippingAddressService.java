package com.bookstore.service;

import com.bookstore.domain.MemShipping;
import com.bookstore.domain.ShippingAddress;

public interface ShippingAddressService {
	ShippingAddress setByUserShipping(MemShipping memShipping, ShippingAddress shippingAddress);
}
