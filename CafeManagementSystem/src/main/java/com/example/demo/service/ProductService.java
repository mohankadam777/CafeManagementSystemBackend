package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductWrapper;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	public ResponseEntity<List<ProductWrapper>> getProducts() {
		try {
				return new ResponseEntity<List<ProductWrapper>> (productRepo.getAllProducts(),HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<String> addProducts(Map<String, String>reqMap) {
	try {
		if(jwtFilter.isAdmin()) {
			if(validateMap(reqMap,false)) {
				productRepo.save(mapProduct(reqMap, false));
				return new ResponseEntity<String>("Products Added successfully",HttpStatus.CREATED);
			}
		return new ResponseEntity<String>("Invalid Data",HttpStatus.BAD_REQUEST);
		}return new ResponseEntity<String> ("Unauthorized Access",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<String> updateProducts(Map<String, String>reqMap) {
	try {
		if(jwtFilter.isAdmin()) {
			if(validateMap(reqMap, true)) {
				Optional<Product> optional= productRepo.findById(Integer.parseInt(reqMap.get("id")));
				if(!optional.isEmpty()) {
					Product product = mapProduct(reqMap, true);
					product.setStatus(optional.get().getStatus());
					productRepo.save(product);
					return new ResponseEntity<String>("Product updated successfully",HttpStatus.BAD_REQUEST);
				}return new ResponseEntity<String>("Product id not found",HttpStatus.BAD_REQUEST);
			}return new ResponseEntity<String>("Invalid Data",HttpStatus.BAD_REQUEST);
		}return new ResponseEntity<String>("Unauthorized access",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public  ResponseEntity<String> deleteById(Integer id) {
		try {
			if (jwtFilter.isAdmin()) {
				Optional<Product> product= productRepo.findById(id);
				if(product.isPresent()) {
					productRepo.deleteById(id);
					return new ResponseEntity<String>("Product deleted successfully",HttpStatus.OK);
				}return new ResponseEntity<String>("Product not found",HttpStatus.BAD_REQUEST);
			}return new ResponseEntity<String>("Uauthorized Access",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	public ResponseEntity<String> updateStatus(Map<String, String>reqMap) {
		try {
			if(jwtFilter.isAdmin()) {
					Optional<Product> optional= productRepo.findById(Integer.parseInt(reqMap.get("id")));
					if(!optional.isEmpty()) {
						System.err.println(reqMap.get("status"));
						productRepo.updateStatus(reqMap.get("status"), Integer.parseInt(reqMap.get("id")));
						return new ResponseEntity<String>("Product Status updated successfully",HttpStatus.BAD_REQUEST);
					}return new ResponseEntity<String>("Product id not found",HttpStatus.BAD_REQUEST);		
			}return new ResponseEntity<String>("Unauthorized access",HttpStatus.UNAUTHORIZED);
			} catch (Exception e) {
//				System.err.println(e);
				e.printStackTrace();
			}
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	public ResponseEntity<List<ProductWrapper>> getProductsByCategory(Integer id) {
		try {
				
				return new ResponseEntity<List<ProductWrapper>> (productRepo.getProductsByCategoryId(id),HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public ResponseEntity<ProductWrapper> getProductsByProduct(Integer id) {
		try {
			ProductWrapper pW = productRepo.getByProductId(id);;
			if(pW!=null){
				
				return new ResponseEntity<ProductWrapper> (pW,HttpStatus.OK);
			}
			return new ResponseEntity<ProductWrapper> (new ProductWrapper(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return new ResponseEntity<ProductWrapper>(new ProductWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/////////////////////////////////////////////////////////////////////////////
	public Boolean  validateMap(Map<String, String> reqMap,boolean isAdd) {
		if(reqMap.containsKey("name")&&reqMap.containsKey("categoryId")&&reqMap.containsKey("price")) {
			if(reqMap.containsKey("id")&&isAdd) {
				return true;
			}else if(!isAdd) {
				return true;
			}
		}	
	return false;
	} 
	public Product mapProduct(Map<String, String>reqMap,boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(reqMap.get("categoryId")));
		Product product = new Product();
		product.setCategory(category);
		if(isAdd) {
			product.setId(Integer.parseInt(reqMap.get("id")));
		}else {
			product.setStatus("true");
		}
		product.setName(reqMap.get("name"));
		product.setDescription(reqMap.get("description"));
		product.setPrice(Integer.parseInt(reqMap.get("price")));
		return product;
	}
}
