package com.example.system.model;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import Util.Util;

@Document(collection="user")
public class User {
	@Id
	private Long id;
	private String name , email , pass, token ,salt;
	private Boolean isTeacher;
	private List<Course> createdCourseList = new  ArrayList<>();
	private List<Purchase> purchaseList = new ArrayList<>();
	
	public User() {
		this("","","","",false);
	}

	public User( String name, String email, String pass, String token, boolean isTeacher) {
		this.id = Util.generatedUniqueLong();
		this.name = name;
		this.email = email;
		this.pass = pass;
		this.token = token;
		this.isTeacher = isTeacher;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Course> getCreatedCourseList() {
		return createdCourseList;
	}

	public List<Purchase> getPurchaseList() {
		return purchaseList;
	}

}
