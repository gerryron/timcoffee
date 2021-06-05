package com.example.timcoffee.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("note")
	private String note;

	@SerializedName("orderDetails")
	private List<OrderDetailsItem> orderDetails;

	@SerializedName("nama")
	private String nama;

	@SerializedName("nomerHp")
	private String nomerHp;

	@SerializedName("pickupDate")
	private String pickupDate;

	@SerializedName("id")
	private int id;

	@SerializedName("orderDate")
	private String orderDate;

	@SerializedName("status")
	private int status;

	public void setNote(String note){
		this.note = note;
	}

	public String getNote(){
		return note;
	}

	public void setOrderDetails(List<OrderDetailsItem> orderDetails){
		this.orderDetails = orderDetails;
	}

	public List<OrderDetailsItem> getOrderDetails(){
		return orderDetails;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNomerHp(String nomerHp){
		this.nomerHp = nomerHp;
	}

	public String getNomerHp(){
		return nomerHp;
	}

	public void setPickupDate(String pickupDate){
		this.pickupDate = pickupDate;
	}

	public Object getPickupDate(){
		return pickupDate;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setOrderDate(String orderDate){
		this.orderDate = orderDate;
	}

	public String getOrderDate(){
		return orderDate;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Order{" + 
			"note = '" + note + '\'' + 
			",orderDetails = '" + orderDetails + '\'' + 
			",nama = '" + nama + '\'' + 
			",nomerHp = '" + nomerHp + '\'' + 
			",pickupDate = '" + pickupDate + '\'' + 
			",id = '" + id + '\'' + 
			",orderDate = '" + orderDate + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}