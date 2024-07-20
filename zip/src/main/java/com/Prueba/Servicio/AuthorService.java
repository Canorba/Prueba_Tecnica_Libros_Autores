package com.Prueba.Servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Prueba.Modelo.Author;
import com.Prueba.Reposotorio.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	 public void addAuthor(Integer authorID, String name) {
	        authorRepository.addAuthor(authorID, name);
	    }

	 public Author updateAuthor(Author author) {
	        // Llama al repositorio para actualizar el autor
	        return authorRepository.updateAuthor(author);
	    }

	 public void deleteAuthor(Integer authorID) {
	        authorRepository.deleteAuthor(authorID);
	    }

		/*
		 * public Author getAuthor(Integer authorID) { return
		 * authorRepository.getAuthor(authorID); }
		 */
}
