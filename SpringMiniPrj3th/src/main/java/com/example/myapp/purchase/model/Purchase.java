package com.example.myapp.purchase.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Purchase {
	private String userId;
	private String userName;
	private String userAddress;
	private String userPhone;
	
	private int productId;
	private String productName;
	private int productPrice;
	private String description;
	
	private int imageId;
	private String imageName;
	private long imageSize;
	private String imageType;
	private byte[] imageData;
	
	private int purchaseId;
	private String delivery;
	private Date PurchaseDate;
	private String purchaseState;
	private int totalPrice;
	
	private int purchaseCnt;
	
	private int cartCnt;
	private int cartId;

	private int totalCnt;
	
	@Override
	public String toString() {
		return "Purchase [userId=" + userId + ", userName=" + userName + ", userAddress=" + userAddress + ", userPhone="
				+ userPhone + ", productId=" + productId + ", productName=" + productName + ", productPrice="
				+ productPrice + ", description=" + description + ", imageId=" + imageId + ", imageSize=" + imageSize
				+ ", purchaseId=" + purchaseId + ", delivery=" + delivery + ", PurchaseDate=" + PurchaseDate
				+ ", purchaseState=" + purchaseState + ", totalPrice=" + totalPrice + ", purchaseCnt=" + purchaseCnt
				+ ", cartCnt=" + cartCnt + "]";
	}
	
	
}
