package com.stuffstoreE.service;

import com.stuffstoreE.domain.BillingAddress;
import com.stuffstoreE.domain.MemBilling;

public interface BillingAddressService {
	BillingAddress setByUserBilling(MemBilling memBilling, BillingAddress billingAddress);
}
