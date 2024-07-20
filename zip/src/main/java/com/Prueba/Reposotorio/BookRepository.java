package com.Prueba.Reposotorio;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
	@Autowired
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcCall updateBookJdbcCall; 

    public void addBook(Integer bookID, String title, Integer authorID) {
        String sql = "CALL AddBook(?, ?, ?)";
        jdbcTemplate.update(sql, bookID, title, authorID);
    }

    
    
    public BookRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
		this.updateBookJdbcCall = new SimpleJdbcCall(dataSource)
            .withProcedureName("UpdateBook");
    }
    
    public String updateBook(Integer bookID, String title, Integer authorID) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("p_BookID", bookID);
        params.addValue("p_Title", title);
        params.addValue("p_AuthorID", authorID);

        try {
            updateBookJdbcCall.execute(params);
            return "Book updated successfully";
        } catch (Exception e) {
            return "Error updating book: " + e.getMessage();
        }
    }
    public void deleteBook(Integer bookID) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("DeleteBook");
        
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("p_BookID", bookID);
        
        jdbcCall.execute(inParams);
    }

	/*
	 * public Book getBook(Integer bookID) { SqlParameterSource inParams = new
	 * MapSqlParameterSource().addValue("p_book_id", bookID); Map<String, Object>
	 * result = getBookJdbcCall.execute(inParams); List<Book> books = (List<Book>)
	 * result.get("p_cursor"); return books.isEmpty() ? null : books.get(0); }
	 * 
	 * private static class BookRowMapper implements RowMapper<Book> {
	 * 
	 * @Override public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
	 * Book book = new Book(); book.setBookID(rs.getInt("BookID"));
	 * book.setTitle(rs.getString("Title"));
	 * book.setAuthorID(rs.getInt("AuthorID")); return book; } }
	 */
}
