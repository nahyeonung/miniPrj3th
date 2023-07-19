package com.example.myapp.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.review.model.Review;
import com.example.myapp.review.model.ReviewImage;

@Repository
@Mapper
public interface IReviewRepository {
	List<Review> selectReviewList(int productId);
	
	void insertReview(Review review);
	void insertImageData(ReviewImage reviewImage);
	
	Review getProduct(int productId);
	//Review selectReview(int reviewId);
	//ReviewImage getImage(int reviewImageId);
}
