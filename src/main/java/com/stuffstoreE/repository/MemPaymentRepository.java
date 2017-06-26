package com.stuffstoreE.repository;

import org.springframework.data.repository.CrudRepository;

import com.stuffstoreE.domain.UserPayment;

public interface MemPaymentRepository extends CrudRepository<UserPayment, Long>{

}
