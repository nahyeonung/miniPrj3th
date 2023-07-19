package com.example.myapp.review.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Review {
	private String userId;
	private int productId;
	private int reviewId;
	private String productName;
	private int productPrice;
	private String productDescription;
	private int rate;
	private String content;
	
	private MultipartFile file;
	private int reviewImageId;
	private String reviewImageName;
	private long reviewImageSize;
	private String reviewImageType;
}
