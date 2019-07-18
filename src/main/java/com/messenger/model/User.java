package com.messenger.model;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 2042999134829408137L;
	
	private long userId;
	private String username;
	private String password;
	private int sex;
	private int enabled;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "{\"userId\":"+userId+","
				+ "\"username\":\""+username+"\","
				+ "\"password\":\""+password+"\","
				+ "\"sex\":"+sex+","
				+ "\"enabled\":"+enabled
				+ "}";
	}
	
}
