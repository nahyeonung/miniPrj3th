package com.example.myapp.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.product.service.IProductService;

@Controller
public class productController {
	
	@Autowired
	IProductService productService;
	
	@RequestMapping("/1")
	public String test(Model model) {
		int num = productService.selectCnt();
		System.out.println(num);
		return "index";
	}
}
