package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.coyote.http11.Http11InputBuffer;
import org.hibernate.mapping.SemanticsResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.repo.CategoryRepo;

import io.jsonwebtoken.lang.Strings;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired 
	private JwtFilter jwtFilter;
	
	////////////////////////////////////////////////////////////////////////////////
	public ResponseEntity<String> addCategory(Map<String, String>requestMap) {
		System.err.println(requestMap);
		try {
			if(jwtFilter.isAdmin()) {
			if(validateCategoryMap(requestMap,false)) {
				System.err.println(getCategoryfromMap(requestMap, false));
				categoryRepo.save(getCategoryfromMap(requestMap, false));
				return new ResponseEntity<String>("Category added successfully",HttpStatus.CREATED);
			}	
			}else {
				return new ResponseEntity<String>("Unauthorized Accesss",HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return new ResponseEntity<>("Something went wrng", HttpStatus.CREATED);
	}
	/////////////////////////////////////////////////////////////////////////////////
	public boolean validateCategoryMap(Map<String, String> requestMap ,boolean validateId) {		
		if(requestMap.containsKey("name")) {
			System.out.println(requestMap);
			if (requestMap.containsKey("id")&&validateId) {
				return true;
			}else if (!validateId){
				return true;
			}		
		}
		return  false;
	}
	//////////////////////////////////////////////////////////////////////////////////
	public  Category getCategoryfromMap(Map<String, String>requestMap,boolean isAdd) {
		Category category = new Category();
		if (requestMap.get("id")!=null) {
			
			category.setId(Integer.parseInt( requestMap.get("id")));
		}
		category.setName(requestMap.get("name"));
		
		return category; 
	}
	///////////////////////////////////////////////////////////////////////////////////
	public ResponseEntity<List<Category>> getCategory(String useCase) {
		try {
//			if (false) {
			if (useCase==null) {
				return new ResponseEntity<List<Category>>(categoryRepo.findAll(),HttpStatus.OK);
			}else if (useCase.equals("true")&&useCase!=null) {
				System.err.println("Hello");
				return new ResponseEntity<List<Category>>(categoryRepo.getAllCategory(),HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public ResponseEntity<String> updateCategory(Map<String, String>requestMap) {
		System.err.println(requestMap);
		try {
			if(jwtFilter.isAdmin()) {
				System.err.println(validateCategoryMap(requestMap,true));
			if(validateCategoryMap(requestMap,false)) {
				Optional<Category> catOptional=categoryRepo.findById( Integer.parseInt(requestMap.get("id")) );
		if(!catOptional.isEmpty()) {
			categoryRepo.save(getCategoryfromMap(requestMap, true));		
			return new ResponseEntity<String>("Category updated successfully",HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Category not found",HttpStatus.NO_CONTENT);

			}	
			}else {
				return new ResponseEntity<String>("Unauthorized Accesss",HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return new ResponseEntity<>("Something went wrng", HttpStatus.CREATED);
	}



}
