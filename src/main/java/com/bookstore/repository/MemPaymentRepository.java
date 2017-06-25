package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.UserPayment;

public interface MemPaymentRepository extends CrudRepository<UserPayment, Long>{

}
