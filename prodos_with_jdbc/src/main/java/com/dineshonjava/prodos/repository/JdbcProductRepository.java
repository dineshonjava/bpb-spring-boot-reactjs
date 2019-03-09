/**
 * 
 */
package com.dineshonjava.prodos.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dineshonjava.prodos.domain.Product;

/**
 * @author Dinesh.Rajput
 *
 */
@Repository
public class JdbcProductRepository implements ProductRepository {
	
	private JdbcTemplate jdbcTemplate;

	public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Iterable<Product> findAll() {
		return jdbcTemplate.query("select id, name, type from Product", this::mapRowToProduct);
	}

	@Override
	public Product findOne(String id) {
		return jdbcTemplate.queryForObject("select id, name, type from Product where id=?", this::mapRowToProduct, id);
	}

	@Override
	public Product save(Product product) {
		jdbcTemplate.update(
				"insert into Product (id, name, type) values (?, ?, ?)",
				product.getId(),
				product.getName(),
				product.getType());	
		return product;
	}
	
	private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
		return new Product(
				rs.getString("id"),
				rs.getString("name"),
				rs.getString("type"));
	}
}
