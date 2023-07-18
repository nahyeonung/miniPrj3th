package com.example.myapp.review.model;

import lombok.Data;

@Data
public class Review {
	private String productName;
	private int productPrice;
	private String productDescription;
	private int rate;
	private String content;
	private String reviewImageName;
	private String reviewImageSize;
	private String reviewImageType;
	private boolean reviewImgaeData;
}
