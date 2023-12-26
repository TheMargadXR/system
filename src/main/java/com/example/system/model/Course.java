package com.example.system.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "course")
public class Course {
	private String name , imgUrl ,courseId;
	private Integer price , realPrice ;
	private LocalDate createdDate;
	private List<Lesson> lessonList = new ArrayList<>();
	
	public Course() {
		this( "","" , "" , 0 ,0 ,LocalDate.now());
	}
	public Course(String courseId ,String name, String imgUrl , Integer price , Integer realPrice  , LocalDate createdDate ) {
		this.courseId = courseId;
		this.name = name; 
		this.imgUrl = imgUrl;
		this.price = price ;
		this.realPrice = realPrice;
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(Integer realPrice) {
		this.realPrice = realPrice;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public List<Lesson> getLessonList() {
		return lessonList;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
	
	
}
