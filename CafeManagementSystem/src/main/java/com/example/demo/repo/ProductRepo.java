package com.example.demo.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductWrapper;

import jakarta.transaction.Transactional;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	
	
	@Query("select new  com.example.demo.entity.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")
	List<ProductWrapper> getAllProducts();
	
	@Modifying
	@Transactional
	@Query("update Product p set p.status=:status where id=:id")
	Integer updateStatus(@Param("status") String status,@Param("id") Integer id );
	
	@Query("select new  com.example.demo.entity.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p where p.category.id=:id and p.status='true' ")
	List<ProductWrapper> getProductsByCategoryId(Integer id);
	
	@Query("select new  com.example.demo.entity.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p where p.id=:id and p.status='true' ")
	ProductWrapper getByProductId(Integer id);
	
}
 