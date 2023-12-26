package com.example.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.system.model.*;
import com.example.system.repo.*;


import Scheme.AllCourseScheme;
import Scheme.CourseScheme;
import Scheme.LessonScheme;
import Scheme.LoginResponse;
import Scheme.LoginScheme;
import Scheme.ResponseScheme;
import Scheme.UserScheme;
import Util.PasswordUtils;

@CrossOrigin(origins = "*", allowedHeaders= "*")
@RestController
public class MainController {
	@Autowired
	private UserRepository userRepo;
	
    @PostMapping("/purchase")
	public ResponseScheme addPurchase(@RequestHeader("Authorization") String  token , @RequestBody Purchase body) {
    	try {
			boolean isValid = false;

			List<User> userList = new ArrayList<>();
			 userList = userRepo.findAll();
			 
			 for(User user : userList) {
				 if(user.getId().equals(body.getUserId())) {
					 if(body.getCourseId() >= 0 &&  body.getCourseId() < user.getCreatedCourseList().size())
						 isValid = true;
					 break;
				 }
			 }
			 if(isValid) {
				 for(User user : userList) {
					 if(user.getToken().equals(token) && token.length() > 10) {
						 user.getPurchaseList().add(body);	
						 userRepo.save(user);
				    		return ResponseScheme.getInstance(true);
						 }
				 }
			 }
			 throw new Exception("Course id эсвэл teacher id тааарахгүй байна");
    	}catch(Exception e){
    		return ResponseScheme.getInstance(false, e.getMessage());
    	}
    }
    
	@GetMapping("/cart")
	public List<AllCourseScheme> getCart(@RequestHeader("Authorization") String  token ){
		try {
			
			List<AllCourseScheme> courseResList = new ArrayList<>();
			List<User> userList = new ArrayList<>();
			 userList = userRepo.findAll();
			 
			 for(User user : userList) {
				 if(user.getToken().equals(token) && token.length() > 10) {
					 for(Purchase purchase : user.getPurchaseList()) {
						 AllCourseScheme data = new AllCourseScheme();
						 data.setCourseId(purchase.getCourseId());
						 data.setUserId(purchase.getUserId());
						 data.setCourse(userRepo.findById(purchase.getUserId()).get().getCreatedCourseList().get(purchase.getCourseId()));
						 courseResList.add(data);
					 }
				 }
			 }
			return courseResList;
		}catch(Exception e) {
			return null;
			}
	}
	
	@GetMapping("/getCourseById")
	public Course getCourseById(@RequestParam Integer courseid , @RequestParam Long teacherid, @RequestHeader("Authorization") String token) {
		try {
			boolean isPurchased = false;
			List<Purchase> PurchaseList = new ArrayList<>();
			List<User> userList = new ArrayList<>();
			userList = userRepo.findAll();
			for(User user : userList) {
				if(user.getToken().equals(token) && token.length() > 10) {
					PurchaseList = user.getPurchaseList();
					break;
				}
			}
			
			User teacher = userRepo.findById(teacherid).get();
			Course course = teacher.getCreatedCourseList().get(courseid);
			
			for(Purchase purchase : PurchaseList) {
				if(purchase.getCourseId() == courseid && purchase.getUserId().equals(teacherid)) {
					isPurchased = true;
					break;
				}
			}
			

			if(!isPurchased) {
				for(int i = 0 ; i < course.getLessonList().size() ; i++) {
					if (!course.getLessonList().get(i).getIsFree()) {
						course.getLessonList().get(i).setVideoUrl(null);
					}
				}
			}
			
			return course;
			
		}catch(Exception e) {
			return null;
		}
	}
		
	
    @GetMapping("/course")
    public List<AllCourseScheme> getCourse() {
    	try {
    		List<AllCourseScheme> courseResList = new ArrayList<>();
    		
    		List<User> userList = new ArrayList<>();
    		userList = userRepo.findAll();

    		for(User user : userList) {
    			for(int i = 0; i <  user.getCreatedCourseList().size() ; i++) {
        			AllCourseScheme data = new AllCourseScheme();
        			data.setCourse(user.getCreatedCourseList().get(i));
        			data.setCourseId(i);
        			data.setUserId(user.getId());
        			data.setTeacherName(user.getName());
        			courseResList.add(data);
    			}
    		}
    		return courseResList;
    	}catch (Exception e) {
    		return null;
    	}
    }
    
//    @PostMapping("/course")
//    public ResponseScheme addCourse(@RequestBody CourseScheme scheme ,@RequestHeader("Authorization") String  token ) {
//    	try {
//    		if(scheme.getName() == null)
//    			throw new Exception("Заавал сургалтын нэх оруулах ёстой");
//    		
//    		    List<User> userList = new ArrayList<>();
//    		    userList = userRepo.findAll();
//    		
//    		
//    		for(User user : userList) {
//    			if(user.getToken().equals(token) && token.length() > 10) {
//    				Course course = new Course();
//    				course.setCourseId(scheme.getCourseId());
//    	    		course.setName(scheme.getName());
//    	    		course.setImgUrl(scheme.getImgUrl());
//    	    		course.setPrice(scheme.getPrice());
//    	    		course.setRealPrice(scheme.getRealPrice());
//    	    		
//    	    		user.getCreatedCourseList().add(course);
//    	    		user.getCreatedCourseList().remove(0).getCourseId();
//    	    		System.out.println(user.getCreatedCourseList().get(0).getCourseId());
//    	    		userRepo.save(user);
//    	    		return ResponseScheme.getInstance(true);
//    			}
//    		}
//    		throw new Exception("Нэвтрэх шаардлагатай");
//    	}catch (Exception e) {
//    		return ResponseScheme.getInstance(false,  e.getMessage());
//    	}
//    }
    
    @PostMapping("/course")
    public ResponseScheme addCourse(@RequestBody CourseScheme scheme, @RequestHeader("Authorization") String token) {
        try {
            if (scheme.getName() == null)
                throw new Exception("Заавал сургалтын нэх оруулах ёстой");

            List<User> userList = userRepo.findAll();

            for (User user : userList) {
                if (user.getToken().equals(token) && token.length() > 10) {
                    Course course = new Course();
                    course.setCourseId(scheme.getCourseId());
                    course.setName(scheme.getName());
                    course.setImgUrl(scheme.getImgUrl());
                    course.setPrice(scheme.getPrice());
                    course.setRealPrice(scheme.getRealPrice());

                    user.getCreatedCourseList().add(course);

                    if (!user.getCreatedCourseList().isEmpty()) {
                        String firstCourseId = user.getCreatedCourseList().get(0).getCourseId();
                    }

                    userRepo.save(user);
                    return ResponseScheme.getInstance(true);
                }
            }
            throw new Exception("Нэвтрэх шаардлагатай");
        } catch (Exception e) {
            return ResponseScheme.getInstance(false, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseScheme deleteCourse(@RequestHeader("Authorization") String  token , @PathVariable String courseId) {
    	try {
    		    List<User> userList = new ArrayList<>();
    		    userList = userRepo.findAll();
    		for(User user : userList) {
    			if(user.getToken().equals(token) && token.length() > 10) {
        			for(int i = 0; i <  user.getCreatedCourseList().size() ; i++) {
        				if(user.getCreatedCourseList().get(i).getCourseId().equals(courseId)) {
            	    		user.getCreatedCourseList().remove(i).getCourseId();
            	    		userRepo.save(user);
            	    		return ResponseScheme.getInstance(true);
        				}
        				else {
        					System.out.println("oldsongui");
        				}
        				
        			}
    			}
    		}
    		throw new Exception("Нэвтрэх шаардлагатай");
    	}catch (Exception e) {
    		return ResponseScheme.getInstance(false,  e.getMessage());
    	}
    }
    @PostMapping("/lesson")
    public ResponseScheme addLesson(@RequestBody LessonScheme scheme ,@RequestHeader("Authorization") String  token ){
    	try {
    		List<User> userList = new ArrayList<>();
    		userList = userRepo.findAll();
    		for(User user : userList) {
    			if(user.getToken().equals(token) && token.length() > 10) {
    				if(scheme.getCourseId() <  0 || scheme.getCourseId()  >=  user.getCreatedCourseList()	.size())
    					throw new Exception("Course id буруу байна  .  ");
    				Lesson lesson = new Lesson();
    				lesson.setIsFree(scheme.getIsFree());
    				lesson.setName(scheme.getName());
    				lesson.setTime(scheme.getTime());
    				lesson.setVideoUrl(scheme.getVideoUrl());
    				Course course =user.getCreatedCourseList().get(scheme.getCourseId());
    				course.getLessonList().add(lesson);
    				userRepo.save(user);
    				return ResponseScheme.getInstance(true);
    			}
    		}
    		
    		throw new Exception("Нэвтрэх шаардлагатай");
    	}catch (Exception e) {
    		return ResponseScheme.getInstance(false,  e.getMessage());
    	}
    }

    @PostMapping("/user")
    public ResponseScheme addUser(@RequestBody UserScheme scheme){
    	try {
    		User user = new User();
    		user.setName(scheme.getName());
    		user.setEmail(scheme.getEmail());  
    		
    		String salt = PasswordUtils.getSalt(10);
    		user.setSalt(salt);
    		user.setPass(PasswordUtils.generateSecurePassword(scheme.getPass(), salt));
    		userRepo.insert(user);
    		
    		return ResponseScheme.getInstance(true);
    	}catch(Exception e){
    		return ResponseScheme.getInstance(false, e.getMessage());
    	}
    }
    
//    @PutMapping("/user/{userId}")
//    public ResponseScheme editUser(@RequestBody UserScheme scheme , @PathVariable Long userId){
//    	try {
//    		List<User>  userList = new ArrayList<>();
//    		userList = userRepo.findAll();
//    		for(User user : userList) {
//    			if(user.getId().equals(userId)) {
//    				System.out.println("userID : " + userId);
//    				System.out.println("user.getId : " + user.getId());
//    	    		user.setName(scheme.getName());
//    	    		user.setEmail(scheme.getEmail());  	
//    	    		String salt = PasswordUtils.getSalt(10);
//    	    		user.setSalt(salt);
//    	    		user.setPass(PasswordUtils.generateSecurePassword(scheme.getPass(), salt));
//    	    		userRepo.insert(user);
//    	    		return ResponseScheme.getInstance(true);
//    			}
//    		}
//    		System.out.println("aldaa garlaa");
//    		return ResponseScheme.getInstance(false);
//    	}catch(Exception e){
//    		return ResponseScheme.getInstance(false, e.getMessage());
//    	}
//    }
    
    @PutMapping("/editUser")
    public User editUser(@RequestBody UserScheme scheme, @RequestParam Long Id ,@RequestHeader("Authorization") String token ) {
        try {
            Optional<User> optionalUser = userRepo.findById(Id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setName(scheme.getName());
                user.setEmail(scheme.getEmail());

                // You may want to check if scheme.getPass() is not null before updating the password
                if (scheme.getPass() != null) {
                    String salt = PasswordUtils.getSalt(10);
                    user.setSalt(salt);
                    user.setPass(PasswordUtils.generateSecurePassword(scheme.getPass(), salt));
                }

                userRepo.save(user); // Use save method for update
                return null;
            } else {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
              return null;
        }
    }

    
    @PostMapping("/userLogin")
    public LoginResponse addUser(@RequestBody LoginScheme scheme){
    	try {
    		List<User>  userList = new ArrayList<>();
    		userList = userRepo.findAll();
    		for(User user : userList) {
    			if(scheme.getEmail().equals(user.getEmail())) {
    				if(PasswordUtils.verifyUserPassword(scheme.getPass(),user.getPass(), user.getSalt())) {
    		    		String salt = PasswordUtils.getSalt(10);
    					String token = PasswordUtils.generateSecurePassword(scheme.getEmail() + user.getPass(), salt);
    					user.setToken(token);
    					userRepo.save(user);
    					System.out.println("token : " + user.getToken());
    					System.out.println("scheme pass : " + scheme.getPass());
    					return new LoginResponse(token);
    				}
    			}
    		}
    		throw new Exception("Цахим шуудан эсвэл нууц үг таарахгүй байна .");
    	}catch(Exception e){
    		return new LoginResponse(false, e.getMessage());
    	}
    }
}