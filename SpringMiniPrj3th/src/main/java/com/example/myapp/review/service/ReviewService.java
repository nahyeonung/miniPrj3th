package com.example.myapp.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.myapp.review.dao.IReviewRepository;
import com.example.myapp.review.model.Review;
import com.example.myapp.review.model.ReviewImage;

@Service
public class ReviewService implements IReviewService {

	@Autowired
	IReviewRepository reviewRepository;

	@Transactional
	public void insertReview(Review review) {
		reviewRepository.insertReview(review);
	}

	@Transactional
	public void insertReview(Review review, ReviewImage reviewImage) {
		reviewRepository.insertReview(review);
		if(reviewImage != null && reviewImage.getReviewImageName() != null && !reviewImage.getReviewImageName().equals("")) {
			reviewImage.setReviewId(reviewRepository.selectReviewId());
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

	@Override
	public ReviewImage getImage(int reviewImageId) {
		// TODO Auto-generated method stub
		return reviewRepository.getImage(reviewImageId);
	}

	@Transactional
	public Review selectReview(int reviewId) {
		// TODO Auto-generated method stub
		return reviewRepository.selectReview(reviewId);
	}

	@Override
	public void updateReview(Review review) {
		// TODO Auto-generated method stub
		reviewRepository.updateReview(review);
	}

	@Override
	public void updateReview(Review review, ReviewImage reviewImage) {
		// TODO Auto-generated method stub
		reviewRepository.updateReview(review);
		if(reviewImage != null && reviewImage.getReviewImageName() != null && !reviewImage.getReviewImageName().equals("")) {
			reviewImage.setReviewId(review.getReviewId());
			System.out.println(reviewImage.getReviewId());
			if(reviewImage.getReviewImageId()>0) {
				reviewRepository.updateImageData(reviewImage);
				System.out.println(1);
			} else {
				reviewImage.setReviewImageId(reviewRepository.selectReviewImageId());
				reviewRepository.insertImageData(reviewImage);
			}
		}
	}

	@Transactional
	public void deleteReview(int reviewId) {
		reviewRepository.deleteImageData(reviewId);
		reviewRepository.deleteReview(reviewId);
	}
	
	
}
