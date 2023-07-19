package com.example.myapp.review.service;

import java.util.List;

import com.example.myapp.review.model.Review;
import com.example.myapp.review.model.ReviewImage;

public interface IReviewService {
	List<Review> selectReviewList(int productId);
	
	void insertReview(Review review);
	void insertReview(Review review, ReviewImage reviewImage);
	
	Review getProduct(int productId);
	Review selectReview(int reviewId);
	ReviewImage getImage(int reviewImageId);
	
	void updateReview(Review review);
	void updateReview(Review review, ReviewImage reviewImage);
	
	void deleteReview(int reviewId);
}
