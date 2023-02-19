package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ProductController {

	
	@Autowired
	private ProductService productService;
	
@GetMapping
public ResponseEntity<List<ProductWrapper>> getProducts(){
	return productService.getProducts();
}

@PostMapping 
public ResponseEntity<String> addProducts(@RequestBody Map<String, String>reqMap ){
	return productService.addProducts(reqMap);
}

@PutMapping 
public ResponseEntity<String> updateProducts(@RequestBody Map<String, String>reqMap ){
	return productService.updateProducts(reqMap);
}

}
