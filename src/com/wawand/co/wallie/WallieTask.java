package com.wawand.co.wallie;

public class WallieTask {
	
	private String email    = "";
	private String password = "";
	private String comment  = "";
	private String team     = "";
	private String hours    = "";
	
	public void setEmail(String email) {
	  this.email = email;
	}
	
	public void setPassword(String password) {
	  this.password = password;
	}
	
	public void setComment(String comment) {
	  this.comment = comment;
	}
	
	public void setTeam(String team) {
	  this.team = team;    
	}
	
	public void setHours(String hours) {
	  this.hours = hours;   
	}
	
	public String getEmail() {
	  return email;   
	}
	
	public String getPassword() {
	  return password;
	}
	
	public String getComment() {
	  return comment; 
	}
	
	public String getTeam() {
	  return team;    
	}
	
	public String getHours() {
	  return hours;   
	}
}
