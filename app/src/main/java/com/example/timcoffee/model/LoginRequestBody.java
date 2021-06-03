package com.example.timcoffee.model;

import com.google.gson.annotations.SerializedName;

public class LoginRequestBody{

	@SerializedName("password")
	private String password;

	@SerializedName("username")
	private String username;

	public LoginRequestBody(String username, String password) {
		this.password = password;
		this.username = username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"LoginRequestBody{" + 
			"password = '" + password + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}