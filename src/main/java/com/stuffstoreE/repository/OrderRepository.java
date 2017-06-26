package com.stuffstoreE.repository;

import org.springframework.data.repository.CrudRepository;

import com.stuffstoreE.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
