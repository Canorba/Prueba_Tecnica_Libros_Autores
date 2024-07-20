package com.Prueba.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Prueba.Modelo.Book;
import com.Prueba.Servicio.BookService;

@RestController
@RequestMapping("/api/libros")
public class BookController {

	@Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            if (book.getBookID() == null || book.getTitle() == null || book.getAuthorID() == null) {
                return new ResponseEntity<>("BookID, Title, and AuthorID cannot be null", HttpStatus.BAD_REQUEST);
            }
            bookService.addBook(book.getBookID(), book.getTitle(), book.getAuthorID());
            return new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Integer id, @RequestBody Book bookRequest) {
        bookService.updateBook(id, bookRequest.getTitle(), bookRequest.getAuthorID());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

	/*
	 * @GetMapping("/{id}") public Book getBook(@PathVariable Integer id) { return
	 * bookService.getBook(id); }
	 */
}
