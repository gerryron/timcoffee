package com.example.timcoffee.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class OrderDetailsItem{

	@SerializedName("price")
	private BigDecimal price;

	@SerializedName("id")
	private int id;

	@SerializedName("productName")
	private String productName;

	public OrderDetailsItem(){

	}
	
	public OrderDetailsItem(Product product) {
		this.productName = product.getName();
		this.price = product.getPrice();
	}

	public void setPrice(BigDecimal price){
		this.price = price;
	}

	public BigDecimal getPrice(){
		return price;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	@Override
 	public String toString(){
		return 
			"OrderDetailsItem{" + 
			"price = '" + price + '\'' + 
			",id = '" + id + '\'' + 
			",productName = '" + productName + '\'' + 
			"}";
		}
}