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

import com.Prueba.Modelo.Author;
import com.Prueba.Servicio.AuthorService;

@RestController
@RequestMapping("/api/autores")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@PostMapping
	public ResponseEntity<String> addAuthor(@RequestBody Author author) {
		try {
			if (author.getAuthorID() == null || author.getName() == null) {
				return new ResponseEntity<>("AuthorID and Name cannot be null", HttpStatus.BAD_REQUEST);
			}
			authorService.addAuthor(author.getAuthorID(), author.getName());
			return new ResponseEntity<>("Author added successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Author> updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
		author.setAuthorID(id);
		Author updatedAuthor = authorService.updateAuthor(author);
		return ResponseEntity.ok(updatedAuthor);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAuthor(@PathVariable Integer id) {
		try {
			authorService.deleteAuthor(id);
			return ResponseEntity.ok("Author deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	/*
	 * @GetMapping("/{id}") public Author getAuthor(@PathVariable Integer id) {
	 * return authorService.getAuthor(id); }
	 */
}
