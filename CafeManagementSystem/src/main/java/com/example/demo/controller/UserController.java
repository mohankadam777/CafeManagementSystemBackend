package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4200")
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
		return new ResponseEntity<String>("Something went wrong in  conntroller",HttpStatus.INTERNAL_SERVER_ERROR) ;
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<Map<String,String>> login(@RequestBody(required=true) Map<String, String> reqMap){
		try {
			
			System.out.println("in login method");
			return	userService.login(reqMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap< String, String> resultHashMap= new HashMap<>();
		resultHashMap.put("message", "Somehing went wrong");
		return new ResponseEntity<Map<String,String>>(resultHashMap,HttpStatus.INTERNAL_SERVER_ERROR) ;
	}

	
	
	@GetMapping("/demo")
	public String demo() {
		return "Hello world";
	}
	
	@GetMapping(path="")
	public List<User>  getUser() {	
		return this.userService.getUser();
	}
	
	@PostMapping(path="/add")
	public User  addUser(@RequestBody User user) {	
		return this.userService.addUser(user);
	}
	
	@PostMapping(path="/changePassword")
	public ResponseEntity<String>  changePassword(@RequestBody Map<String, String> reqMap) {	
		return this.userService.changePassword(reqMap);
	}
	
	@PutMapping(path="/update")
	public User  updateUser(@RequestBody User user) {	
		return this.userService.updateUser(user);
	}
	
	@DeleteMapping(path="/user/{id}")
	public void  deleteUser(@PathVariable Long id) {	
		this.userService.deleteUser(id);
	}
	
	
	//chckToken
	@GetMapping("/checkToken")
	public ResponseEntity<String> checkToken(){
	return this.userService.checkToken();
	}
	
}
