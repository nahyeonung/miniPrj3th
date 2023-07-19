package com.example.myapp.review.model;

import lombok.Data;

@Data
public class ReviewImage {
	private int reviewImageId;
	private int reviewId;
	private String reviewImageName;
	private String reviewImageType;
	private long reviewImageSize;
	private byte[] reviewImageData;
}
