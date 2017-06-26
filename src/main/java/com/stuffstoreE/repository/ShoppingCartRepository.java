package com.stuffstoreE.repository;

import org.springframework.data.repository.CrudRepository;

import com.stuffstoreE.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}
