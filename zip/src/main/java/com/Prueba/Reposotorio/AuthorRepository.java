package com.Prueba.Reposotorio;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.Prueba.Modelo.Author;

import jakarta.annotation.PostConstruct;

@Repository
public class AuthorRepository {

	private final JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall updateAuthorJdbcCall;

	@PostConstruct
	public void init() {
		updateAuthorJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("UpdateAuthor").declareParameters(
				new SqlParameter("p_AuthorID", Types.NUMERIC), new SqlParameter("p_Name", Types.VARCHAR));
		/*
		 * getAuthorJdbcCall = new
		 * SimpleJdbcCall(jdbcTemplate).withProcedureName("UpdateAuthor").
		 * declareParameters( new SqlParameter("p_AuthorID", Types.NUMERIC), new
		 * SqlParameter("p_Name", Types.VARCHAR));
		 */
	}

	public AuthorRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void addAuthor(Integer authorID, String name) {
		String sql = "CALL AddAuthor(?, ?)";
		jdbcTemplate.update(sql, authorID, name);
	}

	public Author updateAuthor(Author author) {
		Map<String, Object> params = new HashMap<>();
		params.put("p_AuthorID", author.getAuthorID());
		params.put("p_Name", author.getName());

		updateAuthorJdbcCall.execute(params);
		return author;
	}

	public void deleteAuthor(Integer authorID) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("DeleteAuthor");

		Map<String, Object> inParams = new HashMap<>();
		inParams.put("p_AuthorID", authorID);

		jdbcCall.execute(inParams);
	}

	/*
	 * public Author getAuthor(Integer authorID) { Map<String, Object> params = new
	 * HashMap<>(); params.put("p_AuthorID", authorID);
	 * 
	 * Map<String, Object> result = getAuthorJdbcCall.execute(new
	 * MapSqlParameterSource(params)); ResultSet rs = (ResultSet)
	 * result.get("p_Result");
	 * 
	 * // Mapear el primer resultado del ResultSet a un Author try { if (rs.next())
	 * { return new AuthorRowMapper().mapRow(rs, 1); } } catch (SQLException e) {
	 * e.printStackTrace(); // Manejar la excepción de manera adecuada }
	 * 
	 * return null; }
	 * 
	 * public static class AuthorRowMapper implements RowMapper<Author> {
	 * 
	 * @Override public Author mapRow(ResultSet rs, int rowNum) throws SQLException
	 * { Author author = new Author(); author.setAuthorID(rs.getInt("AuthorID"));
	 * author.setName(rs.getString("Name")); return author; } }
	 */
}
