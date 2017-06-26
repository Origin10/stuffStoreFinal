package com.stuffstoreE.service;

import java.util.List;

import com.stuffstoreE.domain.Book;
import com.stuffstoreE.domain.CartItem;
import com.stuffstoreE.domain.Order;
import com.stuffstoreE.domain.ShoppingCart;
import com.stuffstoreE.domain.User;

public interface CartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addBookToCartItem(Book book, User user, int qty);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
	
	List<CartItem> findByOrder(Order order);
}
