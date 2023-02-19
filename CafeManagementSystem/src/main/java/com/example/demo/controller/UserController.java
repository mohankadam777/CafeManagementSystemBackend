package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;


@RestController
@RequestMapping(path="/user")
public class UserController {

	
	@Autowired
	UserService userService;
	
	@PostMapping(path = "/signup")
	public ResponseEntity<String> signUp(@RequestBody(required=true) Map<String, String> reqMap){
		try {
			return	userService.signUp(reqMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Somehing went wrong",HttpStatus.INTERNAL_SERVER_ERROR) ;
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<String> login(@RequestBody(required=true) Map<String, String> reqMap){
		try {
			return	userService.login(reqMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Somehing went wrong",HttpStatus.INTERNAL_SERVER_ERROR) ;
	}

	
	
	@GetMapping("/demo")
	public String demo() {
		return "Hello world";
	}
	
	@GetMapping(path="")
	public List<User>  getUser() {	
		return this.userService.getUser();
	}
	
	@PostMapping(path="/user")
	public User  addUser(@RequestBody User user) {	
		return this.userService.addUser(user);
	}
	
	@PutMapping(path="/user")
	public User  updateUser(@RequestBody User user) {	
		return this.userService.updateUser(user);
	}
	
	@DeleteMapping(path="/user/{id}")
	public void  deleteUser(@PathVariable Long id) {	
		this.userService.deleteUser(id);
	}
	
}
