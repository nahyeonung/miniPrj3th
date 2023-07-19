package com.example.myapp.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.myapp.review.dao.IReviewRepository;
import com.example.myapp.review.model.Review;
import com.example.myapp.review.model.ReviewImage;

@Service
public class ReviewService implements IReviewService {

	@Autowired
	IReviewRepository reviewRepository;

	@Transactional
	public void insertReview(Review review) {
		System.out.println(review);
		reviewRepository.insertReview(review);
	}

	@Transactional
	public void insertReview(Review review, ReviewImage reviewImage) {
		reviewRepository.insertReview(review);
		if(reviewImage != null && reviewImage.getReviewImageName() != null && !reviewImage.getReviewImageName().equals("")) {
			reviewImage.setReivewId(review.getReviewId());
			reviewRepository.insertImageData(reviewImage);
		}
	}

	@Override
	public Review getProduct(int productId) {
		// TODO Auto-generated method stub
		return reviewRepository.getProduct(productId);
	}

	@Override
	public List<Review> selectReviewList(int productId) {
		return reviewRepository.selectReviewList(productId);
	}

	
}
