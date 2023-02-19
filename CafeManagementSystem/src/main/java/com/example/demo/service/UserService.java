package com.example.demo.service;

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
	public  ResponseEntity<String> login(Map<String, String> requestMap) {
//		log.info("Inside signup",requestMap)/;
		try {	
			Authentication authentication=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));	
			if(authentication.isAuthenticated()) {
				
				//***************Thukpatii*********//////
				customerUserDetailsService.loadUserByUsername(requestMap.get("email"));
				
				System.out.println(customerUserDetailsService.getUserDetails());
				if(customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
					return new ResponseEntity<String>("{\"token\":\""+
							jwtService.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole())+"\"}",
							HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("{\"message\":\""+"Wait for Admin approval"+"\"}",HttpStatus.BAD_REQUEST);
				}
			}
//			User user = userRepo.findByEmail(requestMap.get("email"));
	
//		return null;	
		} catch (Exception e) {
		// TODO: handle exception
			System.out.println(e);
//			e.printStackTrace();
	}
		return new ResponseEntity<String>("Soething went wrong",HttpStatus.BAD_REQUEST);
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
