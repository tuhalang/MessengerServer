package com.messenger.model;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 2042999134829408137L;
	
	private long userId;
	private String username;
	private String password;
	private boolean sex;
	private boolean enabled;
	
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
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
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
