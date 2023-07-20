package com.example.myapp.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		System.out.println(cartList);
		return "cart/list";
	}
	
	@RequestMapping(value="/cart/insert", method=RequestMethod.POST)
	public String insertCart(Cart cart, RedirectAttributes redirectAttrs) {
		cart.setUserId("test");
		cartService.insertCart(cart);
		return "redirect:/cart/" + cart.getUserId();
	}
	
	@PostMapping("/cart/update")
	public String updateCart(Cart cart) {
		cart.setUserId("gildong");
		System.out.println(cart.getUserId());
		System.out.println(cart.getCartCnt());
		cartService.updateCart(cart);
		return "redirect:/cart/gildong";
	}
}
