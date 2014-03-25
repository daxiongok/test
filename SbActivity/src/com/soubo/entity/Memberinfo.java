package com.soubo.entity;

public class Memberinfo {
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String grade_points;
	private String userName;
	private String user_grade;
	public String getUsername() {
		return userName;
	}
	public void setUsername(String username) {
		this.userName = username;
	}
	public String getUser_grade() {
		return user_grade;
	}
	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}
	public String getGrade_points() {
		return grade_points;
	}
	public void setGrade_points(String grade_points) {
		this.grade_points = grade_points;
	}

	
	public Memberinfo(){
		super();
	}
     public Memberinfo(String username,String user_grade,String grade_points){
    	 
    	 super();
    	 this.userName=username;
    	 this.user_grade=user_grade;
    	 this.grade_points=grade_points;
    	 
     }
     
}
