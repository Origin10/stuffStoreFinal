package com.stuffstoreE.service;

import com.stuffstoreE.domain.MemShipping;
import com.stuffstoreE.domain.ShippingAddress;

public interface ShippingAddressService {
	ShippingAddress setByUserShipping(MemShipping memShipping, ShippingAddress shippingAddress);
}
