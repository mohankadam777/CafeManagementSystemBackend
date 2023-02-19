package com.example.demo.repo;

import java.util.List;

import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Category;

public interface CategoryRepo  extends JpaRepository<Category, Integer>{

	@Query("from Category c where c.id in (select p.category from Product p where p.status='true')")
	List<Category> getAllCategory();
}
