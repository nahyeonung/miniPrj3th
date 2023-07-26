package com.example.myapp.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.cart.model.Cart;
import com.example.myapp.cart.service.ICartService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	
	@Autowired
	ICartService cartService;
	
	@RequestMapping("/cart")
	public String selectCartList(Model model, HttpSession session) {
		String userId = (String)session.getAttribute("userId");
		if(userId != null && !userId.equals("")) {
			List<Cart> cartList = cartService.selectCartList(userId);
			model.addAttribute("cartList", cartList);
			model.addAttribute("session", session);
			return "cart/list";
		}else {
			model.addAttribute("message", "NOT_LOGIN_USER");
			return "user/login";
		}
	}
	
	@RequestMapping(value="/cart/insert", method=RequestMethod.POST)
	public String insertCart(Cart cart, RedirectAttributes redirectAttrs, HttpSession session) {
		try {
			String userId = (String)session.getAttribute("userId");
			if(userId != null && !userId.equals("")) {
				cartService.insertCart(cart);
				return "redirect:/cart";
			}else {
				return "user/login";
			}
		} catch(Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/cart";
	}
	
	@PostMapping("/cart/update")
	public String updateCart(Cart cart) {
		cartService.updateCart(cart);
		return "redirect:/cart";
	}
	
	@PostMapping("/cart/delete")
	public String deleteCart(int cartId) {
		cartService.deleteCart(cartId);
		return "redirect:/cart";
	}
}
