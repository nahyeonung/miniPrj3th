package com.example.myapp.cart.model;

import lombok.Data;

@Data
public class Cart {
	private String userId;
	private String userName;
	private String productName;
	private int productPrice;
	private int cartCnt;
	private int totalPrice;
}
