package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.parser.Entity;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired 
	JwtFilter jwtFilter;
	
	
	@PostMapping
	public ResponseEntity<String> addCategory(@RequestBody Map<String,String>requestMap) {
//		System.err.println("in controller");
//		jwtFilter.isAdmin();
		return categoryService.addCategory(requestMap);
	}
	
	
	@GetMapping
	public ResponseEntity<List<Category>> getCategory(@RequestParam(required = false)String useCase){
//		System.err.println("in controller2");
//		jwtFilter.isAdmin();       
//	List<Category> list=	(List<Category>) categoryService.getCategory(useCase);
//		return new ResponseEntity<>(list,HttpStatus.OK);
	return categoryService.getCategory(useCase);
	}
	
	@PutMapping
	public ResponseEntity<String> getCategory(@RequestBody Map<String,String>requestMap){
		return categoryService.updateCategory(requestMap);
	}
}
