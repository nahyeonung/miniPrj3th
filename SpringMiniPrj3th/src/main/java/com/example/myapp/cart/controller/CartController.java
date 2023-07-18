package com.example.myapp.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.myapp.cart.model.Cart;
import com.example.myapp.cart.service.ICartService;

@Controller
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	@RequestMapping("/cart/{userId}")
	public String selectCartList(@PathVariable String userId, Model model) {
		List<Cart> cartList = cartService.selectCartList(userId);
		model.addAttribute("cartList", cartList);
		return "cart/list";
	}
	
}
