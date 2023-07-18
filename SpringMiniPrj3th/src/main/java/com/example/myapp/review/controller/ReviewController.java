package com.example.myapp.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.review.model.Review;
import com.example.myapp.review.service.IReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	IReviewService reviewService;
	
	@RequestMapping("review/{productId}")
	public String selectReviewList(@PathVariable int productId, Model model) {
		List<Review> reviewList = reviewService.selectReviewList(productId);
		model.addAttribute("reviewList", reviewList);
		return "review/list";
	}
	
}
