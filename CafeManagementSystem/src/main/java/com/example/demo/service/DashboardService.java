package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.repo.BillRepo;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ProductRepo;

@Service
public class DashboardService {
	
@Autowired
private ProductRepo productRepo;

@Autowired
private CategoryRepo categoryRepo;

@Autowired 
private BillRepo billRepo;

public ResponseEntity<Map<String, Long>>getDashboardDetails(){
	try {
		Map<String, Long>result= new HashMap<>();
		result.put("product", productRepo.count());
		result.put("category", categoryRepo.count());
		result.put("bill", billRepo.count());
		return new ResponseEntity<>(result,HttpStatus.OK);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	

}
}
