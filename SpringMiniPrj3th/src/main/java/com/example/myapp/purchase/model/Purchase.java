package com.example.myapp.purchase.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Purchase {
	private int purchaseId;
	private String userId;
	private String delivery;
	private Date purchaseDate;
	private String purchaseState;
	private int totalPrice;
}
