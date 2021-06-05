package com.example.timcoffee.model;

import com.google.gson.annotations.SerializedName;

public class OrderUpdateStatusRequest{

	@SerializedName("status")
	private String status;

	public OrderUpdateStatusRequest(String status) {
		this.status = status;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"OrderUpdateStatusRequest{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}