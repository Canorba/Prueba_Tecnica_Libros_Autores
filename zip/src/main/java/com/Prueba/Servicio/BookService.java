package com.Prueba.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Prueba.Reposotorio.BookRepository;

@Service
public class BookService {

	@Autowired
    private BookRepository bookRepository;

	public void addBook(Integer bookID, String title, Integer authorID) {
        bookRepository.addBook(bookID, title, authorID);
    }

	public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void updateBook(Integer bookID, String title, Integer authorID) {
        bookRepository.updateBook(bookID, title, authorID);
    }

    public void deleteBook(Integer bookID) {
        bookRepository.deleteBook(bookID);
    }

	/*
	 * public Book getBook(Integer bookID) { return bookRepository.getBook(bookID);
	 * }
	 */
}
