package com.example.myapp.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.review.dao.IReviewRepository;
import com.example.myapp.review.model.Review;

@Service
public class ReviewService implements IReviewService {

	@Autowired
	IReviewRepository reviewRepository;
	
	@Override
	public List<Review> selectReviewList(int productId) {
		return reviewRepository.selectReviewList(productId);
	}

}
