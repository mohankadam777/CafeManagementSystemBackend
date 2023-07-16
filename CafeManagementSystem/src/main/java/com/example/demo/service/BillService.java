package com.example.demo.service;

import java.util.Map;

import javax.swing.text.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bill;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.repo.BillRepo;
import com.example.demo.utils.CafeUtils;

@Service
public class BillService {
	
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	public ResponseEntity<String> generateReport(Map< String, Object> reqMap) {
		try {
			String fileName;
			if(validateMap(reqMap)) {
				if(reqMap.containsKey("isGenerated")&& (!(Boolean)reqMap.get("isGenerated"))) {
				fileName=(String)reqMap.get("uuid");
				}else {
					fileName = CafeUtils.getUUID();
					reqMap.put("uuid", fileName);
					insertBill(reqMap);
				}
				String basicDetail="Name : "+reqMap.get("name")+"\n"+"Contact Number : "+reqMap.get("contactNumber")+
									"\n"+"Email : "+reqMap.get("email")+"Payment Method : "+reqMap.get("paymentMethod");
				
//				Document document= new Document();/
			}return new ResponseEntity<String>("Required Data not found",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	private boolean validateMap(Map<String, Object>reqMap) {
		return reqMap.containsKey("name")&&
				reqMap.containsKey("contactNumber")&&
				reqMap.containsKey("email")&&
				reqMap.containsKey("paymentMethod")&&
				reqMap.containsKey("productDetails")&&
				reqMap.containsKey("total");
		
	}
	private  void insertBill(Map< String, Object>reqMap) {
		try {
			Bill bill = new Bill();
			bill.setUuid((String)reqMap.get("uuid"));
			bill.setName((String)reqMap.get("name"));
			bill.setEmail((String)reqMap.get("email"));
			bill.setContactNumber((String)reqMap.get("contactNumber"));
			bill.setPaymentMethod((String)reqMap.get("paymentMethod"));
			bill.setProductDetail((String)reqMap.get("productDetails"));
			bill.setTotal(Integer.parseInt((String)reqMap.get("total")));
			bill.setCreatedBy(jwtFilter.getCurrentUser());
			billRepo.save(bill);
		} catch (Exception e) {
			// TODO: handle 
e.printStackTrace();
		}
	}
	
}
