package com.example.myapp.purchase.model;

import java.sql.Date;

import lombok.Data;

@Data
public class PurchaseDetail {
	private String userId;
	private String userName;
	private String userPhone;
	
	private int productId;
	private String productName;
	private int productPrice;
	
	private int imageId;
	private String imageName;
	
	private int purchaseId;
	private String delivery;
	private Date purchaseDate;
	private String purchaseState;
	private int totalPrice;
	
	private int purchaseDetailId;
	private int purchaseCnt;
}
