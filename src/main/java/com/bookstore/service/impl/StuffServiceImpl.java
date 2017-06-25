package com.bookstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.Book;
import com.bookstore.repository.StuffRepository;
import com.bookstore.service.StuffService;

@Service
public class StuffServiceImpl implements StuffService {
    @Autowired
    private StuffRepository stuffRepository;

    public List<Book> findAll() {
        List<Book> bookList = (List<Book>) stuffRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public Book findOne(Long id) {
        return stuffRepository.findOne(id);
    }

    public List<Book> findByCategory(String category) {
        List<Book> bookList = stuffRepository.findByCategory(category);

        List<Book> activeBookList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    public List<Book> blurrySearch(String title) {
        List<Book> bookList = stuffRepository.findByTitleContaining(title);
        List<Book> activeBookList = new ArrayList<>();

        for (Book book : bookList) {
            if (book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    @Override
    public Book save(Book book) {
        return stuffRepository.save(book);
    }

    @Override
    public void removeOne(Long id) {
        stuffRepository.delete(id);
    }


}
