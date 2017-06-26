package com.stuffstoreE.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.stuffstoreE.domain.Book;

public interface StuffRepository extends CrudRepository<Book, Long>{
	List<Book> findByCategory(String category);
	
	List<Book> findByTitleContaining(String title);
}
