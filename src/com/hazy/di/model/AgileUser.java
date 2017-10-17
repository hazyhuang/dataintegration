package com.hazy.di.model;

import java.util.Observable;

public class AgileUser extends Observable{
	
	private String username;
	private String password;
	private String url;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
		setChanged();
		this.notifyObservers();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

}
