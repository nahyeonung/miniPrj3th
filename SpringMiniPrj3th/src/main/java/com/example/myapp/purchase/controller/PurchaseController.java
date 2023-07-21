package com.example.myapp.purchase.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.purchase.model.Purchase;
import com.example.myapp.purchase.service.IPurchaseService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PurchaseController {
	
	@Autowired
	IPurchaseService purchaseService;
	
	@RequestMapping(value="purchase/insert", method=RequestMethod.GET)
	public String InsertPuchase(@RequestParam List<Integer> cartIdList, Purchase purchase, HttpSession session, Model model) {
		String userId = (String)session.getAttribute("userId");
		purchase = purchaseService.selectUserInfo(userId);
		
		List<Purchase> list = purchaseService.selectCartInfo(cartIdList, userId);
		System.out.println(list);
		model.addAttribute("list", list);
		model.addAttribute("purchase", purchase);
		return "purchase/insert";
	}
}
