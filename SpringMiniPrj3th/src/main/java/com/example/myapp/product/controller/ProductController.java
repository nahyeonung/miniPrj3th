package com.example.myapp.product.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadImage;
import com.example.myapp.product.model.UploadProduct;
import com.example.myapp.product.service.IProductService;
import com.example.myapp.review.controller.ReviewController;
import com.example.myapp.review.model.Review;
import com.example.myapp.review.service.IReviewService;

@Controller
public class ProductController {
	static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	IProductService productService;
	
	@Autowired
	IReviewService reviewService;
	
	@RequestMapping("/shop")
	public String getAllProduct(Model model) {
		List<Product> list = productService.selectAllProduct(-1);
		model.addAttribute("productList", list);
		return "product/shop";
	}
	
	@RequestMapping("/file/{fileId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int fileId){
		UploadImage file = productService.getFile(fileId);
		logger.info("getFile" + file.toString());
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getImageType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getImageSize());
		try {
			String encodedFileName = URLEncoder.encode(file.getImageName(), "UTF-8");
			headers.setContentDispositionFormData("attachment", encodedFileName);
		} catch(UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return new ResponseEntity<byte[]>(file.getImageData(), headers, HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/product/productManage", method=RequestMethod.GET)
	public String manage(Model model) {
		List<Category> list = productService.selectAllCategory();
		List<Product> productList = productService.selectAllProduct(-1);
		model.addAttribute("categoryList", list);
		model.addAttribute("productList", productList);
		return "product/product";
	}
	
	@RequestMapping("/shop/{categoryId}")
	public String getProduct(@PathVariable int categoryId, Model model) {
		List<Product> list = productService.selectAllProduct(categoryId);
		System.out.println(list);
		return "product/shop";
	}
	
	@RequestMapping(value="/category/insert", method=RequestMethod.POST)
	public String insertCategory(String categoryName) {
		int row = productService.insertCategory(categoryName);
		System.out.println(row);
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/category/delete", method=RequestMethod.POST)
	public String deleteCategory(@RequestParam List<Integer> categoryId) {
		System.out.println(categoryId);
		if(categoryId.size() > 0) {	
			for(int i=0; i<categoryId.size(); i++) {
				int row = productService.deleteCategory(categoryId.get(i));				
			}
		}
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/product/insert", method=RequestMethod.POST)
	public String insertProduct(UploadProduct product, RedirectAttributes redirectAttrs) throws IOException {
		logger.info(product.getFile().getOriginalFilename());
		try {
			MultipartFile mfile = product.getFile();
			if(mfile != null && !mfile.isEmpty()) {
				UploadImage uploadImage = new UploadImage();
				uploadImage.setImageName(mfile.getOriginalFilename());
				uploadImage.setImageSize(mfile.getSize());
				uploadImage.setImageType(mfile.getContentType());
				uploadImage.setImageData(mfile.getBytes());
				productService.insertProducts(product, uploadImage);
			}else {
				productService.insertProducts(product);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/product/update", method=RequestMethod.POST)
	public String updateProduct(UploadProduct product, RedirectAttributes redirectAttrs) throws IOException{
		logger.info(product.getFile().getOriginalFilename());
		try {
			MultipartFile mfile = product.getFile();
			if(mfile != null && !mfile.isEmpty()) {
				UploadImage uploadImage = new UploadImage();
				uploadImage.setImageName(mfile.getOriginalFilename());
				uploadImage.setImageSize(mfile.getSize());
				uploadImage.setImageType(mfile.getContentType());
				uploadImage.setImageData(mfile.getBytes());
				int row = productService.updateProduct(product, uploadImage);
			}else {
				int row = productService.updateProduct(product);
			}
		}catch(Exception e) {
			logger.error(e.getMessage());
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/product/delete", method=RequestMethod.POST)
	public String deleteProduct(@RequestParam(required=false, defaultValue="") List<Integer> productId) {
		if(productId.size() > 0) {
			for(int i=0; i<productId.size(); i++) {
				int row = productService.deleteProduct(productId.get(i));
			}
		}
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/product/deleteBack", method=RequestMethod.POST)
	public String deleteBackProduct(@RequestParam(required=false, defaultValue="") List<Integer> productId) {
		if(productId.size() > 0) {
			for(int i=0; i<productId.size(); i++) {
				int row = productService.deleteBackProduct(productId.get(i));
			}
		}
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/product/update/{productId}", method=RequestMethod.GET)
	public String updateProduct(@PathVariable int productId) {
		System.out.println(productId);
		Product product = productService.selectProduct(productId);
		return "redirect:/product/productManage";
	}

	@RequestMapping(value="/product/productDetail/{productId}", method=RequestMethod.GET)
	public String selectProduct(@PathVariable int productId, Model model) {
		Product product = productService.selectProduct(productId);
		List<Review> reviewList = reviewService.selectReviewList(productId);
		model.addAttribute("product", product);
		model.addAttribute("reviewList", reviewList);
		return "/product/shop-single";
	}
}
