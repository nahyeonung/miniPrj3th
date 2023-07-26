package com.example.myapp.review.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
	private byte[] reviewImageData;
}
