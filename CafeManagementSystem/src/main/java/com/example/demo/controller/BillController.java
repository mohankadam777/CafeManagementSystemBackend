package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillController {
	
	@Autowired 
	private BillService billService;
	
	@PostMapping("/generateReport")
	public ResponseEntity<String> generateReport(@RequestBody Map<String, String> reqMap){
		
		return null;
		
	}
}
