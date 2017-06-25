package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.domain.StuffToCartItem;
import com.bookstore.domain.CartItem;

@Transactional
public interface StuffToCartItemRepository extends CrudRepository<StuffToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
