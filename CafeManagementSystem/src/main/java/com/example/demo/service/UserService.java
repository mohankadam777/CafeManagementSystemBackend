package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.entity.User;
import com.example.demo.jwt.CustomerUserDetailsService;
import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.JwtService;


@Slf4j
@Service
public class UserService  {

	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private  JwtFilter jwtFilter;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getUser() {
		return this.userRepo.findAll();
	}
	
	public User addUser( User user) {
		return this.userRepo.save(user);
	}
	
	public User updateUser( User user) {
		return this.userRepo.save(user);
	}
	
	public void deleteUser( Long id) {
		this.userRepo.deleteById(id);
	}
	
	/////////////////////////////////////////////////////////
	public  ResponseEntity<String> signUp(Map<String, String> requestMap) {
//		log.info("Inside signup",requestMap)/;
		try {
			
	
		if(validateSignUpMap(requestMap)) {
//			return null;/
			
			
			
			User user = userRepo.findByEmail(requestMap.get("email"));
		if(Objects.isNull(user)) {
			userRepo.save(requestUserfromUserMap(requestMap));
			return new ResponseEntity<String>("Successsfully registered",HttpStatus.OK);
			
		}else {
			return new ResponseEntity<String>("Email already exists",HttpStatus.BAD_REQUEST);
		}
		
		}else {
			return new ResponseEntity<String>("Invalid data",HttpStatus.BAD_REQUEST);
		}
//		return null;	
		} catch (Exception e) {
		// TODO: handle exception
			e.printStackTrace();
	}
		return new ResponseEntity<String>("Soething went wrong",HttpStatus.BAD_REQUEST);
	}
	///////////////////////////////////////Login//////////////////////////////////////////////
	public  ResponseEntity<Map<String,String>> login(Map<String, String> requestMap) {
//		log.info("Inside signup",requestMap);
System.out.println("Inside login method"+requestMap);
HashMap<String, String> resultHashMap= new HashMap<>();
		try {	
			System.out.println("in try");
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));	
			System.out.println("result");
			System.out.println("result"+authentication.isAuthenticated());
			if(authentication.isAuthenticated()) {
				System.out.println("in if");
				
				//***************Thukpatii*********//////
				customerUserDetailsService.loadUserByUsername(requestMap.get("email"));
				
				System.out.println(customerUserDetailsService.getUserDetails());
				if(customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
					
					resultHashMap.put("token", jwtService.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole()));
					return new ResponseEntity<Map<String,String>>(resultHashMap,HttpStatus.OK);
				}else {
					resultHashMap.put("message", "Wait for Admin approval");
					return new ResponseEntity<Map<String,String>>(resultHashMap,HttpStatus.BAD_REQUEST);
				}
				
			}
			resultHashMap.put("message", "Invalid credentials");
			return new ResponseEntity<Map<String,String>>(resultHashMap,HttpStatus.UNAUTHORIZED);

		} catch (Exception e) {
		// TODO: handle exception
			System.out.println(e);
			resultHashMap.put("message", "Something went wrong");
			new ResponseEntity<Map<String,String>>(resultHashMap,HttpStatus.INTERNAL_SERVER_ERROR);
	}
		resultHashMap.put("message", "Something went wrong");
		return new ResponseEntity<Map<String,String>>(resultHashMap,HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<String> checkToken() {
		try {
			return new ResponseEntity<String>("{\"permit\" : \"true\" }",HttpStatus.OK);
//			return new ResponseEntity<String>("true",HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}return new ResponseEntity("Something went wrong",HttpStatus.BAD_REQUEST);
	}
	public ResponseEntity<String> changePassword(Map<String, String>reqMap) {
		try {
			User user = userRepo.findByEmail(jwtFilter.getCurrentUser()) ;
			if(!user.equals(null)) {
				if(user.getPassword().equals(reqMap.get("oldPassword"))) {
					user.setPassword(reqMap.get("oldPassword"));
					userRepo.save(user);
					return new ResponseEntity<String>("Password changed successfully",HttpStatus.OK);
				}return new ResponseEntity<String>("Incorrect Old Password",HttpStatus.BAD_REQUEST);
			}return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
		
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}return new ResponseEntity<String>("Something went wrong", HttpStatus.BAD_REQUEST);
	}
	///////////////////////////////////////////////////////////////////
	public boolean validateSignUpMap(Map<String, String>requestMap) {
	
		if(requestMap.containsKey("name")&&requestMap.containsKey("contactNumber")&&requestMap.containsKey("email")&&requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}
	public User requestUserfromUserMap(Map<String, String>requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(passwordEncoder.encode(requestMap.get("password")));
		user.setStatus("false");
		user.setRole("user");
		return user;
	}
	
	
}
