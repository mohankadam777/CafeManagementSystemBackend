package com.example.demo.entity;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Admin
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="product")
public class Product implements Serializable {

	
	public static final Long serialVersion=1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String description;
	private Integer price;
	private String status;
	
	
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name = "category_fk",nullable = false)
	private Category category;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Product(Integer id, String name, String description, Integer price, String status, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
		this.category = category;
	}


	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
