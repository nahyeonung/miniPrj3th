package com.example.myapp.product.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadProduct;
import com.example.myapp.product.service.IProductService;

@Controller
public class ProductController {
	static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	IProductService productService;
	
	@RequestMapping("/shop")
	public String getAllProduct(Model model) {
		List<Product> list = productService.selectAllProduct(-1);
		System.out.println(list);
		return "product/shop";
	}
	
	@RequestMapping(value="/product/productManage", method=RequestMethod.GET)
	public String manage(Model model) {
		List<Category> list = productService.selectAllCategory();
		List<Product> productList = productService.selectAllProduct(-1);
		model.addAttribute("categoryList", list);
		model.addAttribute("productList", productList);
		return "product/productManage";
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
	public String deleteCategory(int categoryId) {
		int row = productService.deleteCategory(categoryId);
		return "redirect:/product/productManage";
	}
	
	@RequestMapping(value="/product/insert", method=RequestMethod.POST)
	public String insertProduct(UploadProduct product, RedirectAttributes redirectAttrs) throws IOException {
		logger.info(product.getFile().getOriginalFilename());
//		String fileName = file.getOriginalFilename();
//		int fileLength = fileName.length();
//		String fileType = fileName.substring(fileLength-4, fileLength).toUpperCase();
		try {
//			UploadProduct newProduct = new UploadProduct();
//			newProduct.setCategoryId(categoryId);
//			newProduct.setProductName(productName);
//			newProduct.setProductPrice(productPrice);
//			newProduct.setProductStock(productStock);
//			newProduct.setProductDescription(productDescription);
//			newProduct.setImgName(file.getOriginalFilename());
//			newProduct.setImgSize(file.getSize());
//			newProduct.setImgType(file.getContentType());
//			newProduct.setImgData(file.getBytes());
//			productService.insertProducts(newProduct);
		}catch(Exception e) {
			logger.error(e.getMessage());
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/product/productManage";
	}
	

}
