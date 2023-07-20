package com.example.myapp.cart.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
	private int cartId;
	private String userId;
	private int productId;
	private String productName;
	private int productPrice;
	private String productDescription;
	private int cartCnt;
	private int totalPrice;
	
	private MultipartFile file;
	
	private int imageId;
	private String imageName;
	private long imageSize;
	private String imageType;
	private byte[] imageData;
}
