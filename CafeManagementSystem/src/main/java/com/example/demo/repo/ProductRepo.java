package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductWrapper;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	
	@Query("select new  com.example.demo.entity.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")
	List<ProductWrapper> getAllProducts();
}
 