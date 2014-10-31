package com.travel.gate365.model;

public class UserInfo {

	private final String username;
	private final String password;

	public UserInfo(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
