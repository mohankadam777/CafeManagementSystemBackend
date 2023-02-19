package com.example.demo.entity;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery(name="User.findByEmailId",query="select u from User u where u.email=:email")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="userdemo")
public class User implements Serializable{
	
	 private static final long serialVersionUID=1L;
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(name="name")
	String name;
	
	@Column(name="contactNumber")
	String contactNumber;
	
	@Column(name="email")
	String email;
	
	@Column(name="password")
	String password;
	
	@Column(name="status")
	String status;
	
	@Column(name="role")
	String role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User(Long id, String name, String contactNumber, String email, String password, String status, String role) {
		super();
		this.id = id;
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.password = password;
		this.status = status;
		this.role = role;
	}

	public User() {
//		super();s
		// TODO Auto-generated constructor stub
	}
	
	
	

}
