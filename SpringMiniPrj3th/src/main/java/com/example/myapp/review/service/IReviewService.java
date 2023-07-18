package com.example.myapp.review.service;

import java.util.List;

import com.example.myapp.review.model.Review;

public interface IReviewService {
	List<Review> selectReviewList(int productId);
}
