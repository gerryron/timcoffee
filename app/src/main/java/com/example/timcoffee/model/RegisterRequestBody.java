package com.example.timcoffee.model;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestBody{

	@SerializedName("password")
	private String password;

	@SerializedName("role")
	private String role;

	@SerializedName("nomerHp")
	private String nomerHp;

	@SerializedName("name")
	private String name;

	@SerializedName("email")
	private String email;

	public RegisterRequestBody(String name, String nomerHp, String email, String password, String role) {
		this.password = password;
		this.role = role;
		this.nomerHp = nomerHp;
		this.name = name;
		this.email = email;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setNomerHp(String nomerHp){
		this.nomerHp = nomerHp;
	}

	public String getNomerHp(){
		return nomerHp;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"RegisterRequestBody{" + 
			"password = '" + password + '\'' + 
			",role = '" + role + '\'' + 
			",nomerHp = '" + nomerHp + '\'' + 
			",name = '" + name + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}