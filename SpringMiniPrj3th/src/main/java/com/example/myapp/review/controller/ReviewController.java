package com.example.myapp.review.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.review.model.Review;
import com.example.myapp.review.model.ReviewImage;
import com.example.myapp.review.service.IReviewService;

import jakarta.servlet.http.HttpSession;

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
	public String insertArticle(Review review, BindingResult results, RedirectAttributes redirectAttrs, HttpSession session) {
		try {
			reviewService.getProduct(review.getProductId());
			String userId = (String)session.getAttribute("userId");
			review.setUserId(userId);
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
		return "redirect:/product/productDetail/" + review.getProductId();
	}
	
	@RequestMapping("/review/file/{reviewImageId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int reviewImageId) {
		ReviewImage file = reviewService.getImage(reviewImageId);
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getReviewImageType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getReviewImageSize());
		try {
			String encodedFileName = URLEncoder.encode(file.getReviewImageName(), "UTF-8");
			headers.setContentDispositionFormData("attachment", encodedFileName);
		}catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(file.getReviewImageData(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/review/update/{reviewId}", method=RequestMethod.GET)
	public String updateReview(@PathVariable int reviewId, Model model, HttpSession session) {
		Review review = reviewService.selectReview(reviewId);
		String userId = (String)session.getAttribute("userId");
		review.setUserId(userId);
		review.setContent(review.getContent().replace("<br>", "\r\n"));
		model.addAttribute("review", review);
		return "review/update";
	}
	
	@RequestMapping(value="/review/update", method=RequestMethod.POST)
	public String updateReview(Review review, RedirectAttributes redirectAttrs) {
		try {
			review.setContent(review.getContent().replace("\r\n", "<br>"));
			review.setRate(review.getRate());
			MultipartFile mfile = review.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				ReviewImage file = new ReviewImage();
				file.setReviewImageId(review.getReviewImageId());
				file.setReviewImageName(mfile.getOriginalFilename());
				file.setReviewImageSize(mfile.getSize());
				file.setReviewImageType(mfile.getContentType());
				file.setReviewImageData(mfile.getBytes());
				reviewService.updateReview(review, file);
			}else {
				reviewService.updateReview(review);
			}
		}catch(Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/review/"+review.getProductId();
	}
	
	@GetMapping("/review/delete")
	public String deleteReview(int reviewId) {
		reviewService.deleteReview(reviewId);
		return "redirect:/review/" + reviewId;
	}
	
}
