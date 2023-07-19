package com.example.myapp.review.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.review.model.Review;
import com.example.myapp.review.model.ReviewImage;
import com.example.myapp.review.service.IReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	IReviewService reviewService;
	
	@RequestMapping("/review/{productId}")
	public String selectReviewList(@PathVariable int productId, Model model) {
		List<Review> reviewList = reviewService.selectReviewList(productId);
		model.addAttribute("reviewList", reviewList);
		return "review/list";
	}
	
	@RequestMapping(value="/review/insert/{productId}", method=RequestMethod.GET)
	public String insertArticle(@PathVariable int productId, Model model) {
		model.addAttribute("productId", productId);
		return "review/insert";
	}
	
	@RequestMapping(value="/review/insert", method=RequestMethod.POST)
	public String insertArticle(Review review, BindingResult results, RedirectAttributes redirectAttrs) {
		try {
			reviewService.getProduct(review.getProductId());
			review.setUserId("test");
			MultipartFile mfile = review.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				ReviewImage image = new ReviewImage();
				image.setReviewImageName(mfile.getOriginalFilename());
				image.setReviewImageSize(mfile.getSize());
				image.setReviewImageType(mfile.getContentType());
				image.setReviewImageData(mfile.getBytes());
				reviewService.insertReview(review, image);
			}else {
				reviewService.insertReview(review);
			}
		}catch(Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/review/" + review.getProductId();
	}
	
}
