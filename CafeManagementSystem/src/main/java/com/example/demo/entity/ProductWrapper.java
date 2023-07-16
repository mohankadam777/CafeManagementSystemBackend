package com.example.demo.entity;

import lombok.Data;

//@Data
public class ProductWrapper {

	private Integer id;
	private String name;
	private String description;
	private Integer price;
	private String status;
	private Integer categoryId;
	private String categoryNumber;
	
	public ProductWrapper() {
		// TODO Auto-generated constructor stub
	}

	public ProductWrapper(Integer id, String name, String description, Integer price, String status, Integer categoryId,
			String categoryNumber) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.status = status;
		this.categoryId = categoryId;
		this.categoryNumber = categoryNumber;
	}

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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryNumber() {
		return categoryNumber;
	}

	public void setCategoryNumber(String categoryNumber) {
		this.categoryNumber = categoryNumber;
	}

	@Override
	public String toString() {
		return "ProductWrapper [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", status=" + status + ", categoryId=" + categoryId + ", categoryNumber=" + categoryNumber + "]";
	}
	
	
	
	
}
