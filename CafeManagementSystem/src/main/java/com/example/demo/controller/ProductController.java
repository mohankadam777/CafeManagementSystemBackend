package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductWrapper;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200/")
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
@GetMapping("/get")
public ResponseEntity<List<ProductWrapper>> getProducts(){
	
	return productService.getProducts();
}

@PostMapping("/add")
public ResponseEntity<String> addProducts(@RequestBody Map<String, String>reqMap ){
	return productService.addProducts(reqMap);
}

@PutMapping ("/update")
public ResponseEntity<String> updateProducts(@RequestBody Map<String, String>reqMap ){
	return productService.updateProducts(reqMap);
}
@PostMapping("/delete/{id}")
public ResponseEntity<String> deleteProductsById(@PathVariable Integer id ){
	
	return productService.deleteById(id);
}
@PostMapping("/updateStatus")
public ResponseEntity<String> updateStatus(@RequestBody Map<String, String>reqMap ){
	return productService.updateStatus(reqMap);
}
@GetMapping("/getByCategory/{id}")
public ResponseEntity<List<ProductWrapper>> getByCategoryId(@PathVariable Integer id ){
	return productService.getProductsByCategory(id);
}
@GetMapping("/getByProduct/{id}")
public ResponseEntity<ProductWrapper> getByProductId(@PathVariable Integer id ){
	return productService.getProductsByProduct(id);
}
}
