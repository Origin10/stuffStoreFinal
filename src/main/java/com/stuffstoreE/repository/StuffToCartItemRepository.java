package com.stuffstoreE.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.stuffstoreE.domain.StuffToCartItem;
import com.stuffstoreE.domain.CartItem;

@Transactional
public interface StuffToCartItemRepository extends CrudRepository<StuffToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
