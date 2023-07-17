package com.example.myapp.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.product.model.Product;
import com.example.myapp.product.service.IProductService;

@Controller
public class productController {
	
	@Autowired
	IProductService productService;
	
	@RequestMapping("/shop")
	public String getAllProduct(Model model) {
		List<Product> list = productService.selectAllProduct(-1);
		System.out.println(list);
		return "product/shop";
	}
	
	@RequestMapping("/shop/{categoryId}")
	public String getProduct(@PathVariable int categoryId, Model model) {
		List<Product> list = productService.selectAllProduct(categoryId);
		System.out.println(list);
		return "product/shop";
	}
	
	@RequestMapping(value="/category/insert", method=RequestMethod.POST)
	public String insertCategory(String categoryName) {
		System.out.println(categoryName);
//		int row = productService.insertCategory(categoryName);
		return "redirect:/product/productManage";
	}
}
