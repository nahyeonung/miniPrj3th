package com.example.myapp.review.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.review.model.Review;

@Repository
@Mapper
public interface IReviewRepository {
	List<Review> selectReviewList(int productId);
}
