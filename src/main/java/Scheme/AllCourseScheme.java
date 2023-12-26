package Scheme;

import java.time.LocalDate;

import com.example.system.model.Course;


public class AllCourseScheme {
	private Integer  courseId ;
	private Long userId;
	private String name , imgUrl , teacherName;
	private Integer price , realPrice ;
	private LocalDate createdDate;
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
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
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public void setCourse(Course course) {

		this.setName(course.getName());
		this.setImgUrl(course.getImgUrl());
		this.setPrice(course.getPrice());
		this.setRealPrice(course.getRealPrice());
		this.setCreatedDate(course.getCreatedDate());
	}
	
	
	
}
