package com.example.myapp.purchase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.purchase.model.Purchase;
import com.example.myapp.purchase.model.PurchaseDetail;
import com.example.myapp.purchase.service.IPurchaseService;

@Controller
public class PurchaseController {
	
	@Autowired
	IPurchaseService purchaseService;
	
	@RequestMapping(value="/purchase/insert", method=RequestMethod.GET)
	public String insertPurchase() {
		return "purchase/insert";
	}
	
	@RequestMapping(value="/purchase/insert", method=RequestMethod.POST)
	public String insertPurchase(BindingResult results, RedirectAttributes redirectAttrs) {
		Purchase purchase = new Purchase();
		purchase.setPurchaseId(0)
		return "purchase/insert";
	}
	
	@RequestMapping("/purchase/{userId}")
	public String selectAllPurchase(@PathVariable String userId, Model model) {
		List<Purchase> purchaseList = purchaseService.selectAllPurchase(userId);
		model.addAttribute("purchaseList", purchaseList);
		return "purchase/list";
	}
	
	@RequestMapping("/purchase/{purchaseId}/{userId}")
	public String selectPurchase(@PathVariable String userId, @PathVariable int purchaseId, Model model) {
		PurchaseDetail purchaseDetail = purchaseService.selectPurchase(purchaseId, userId);
		model.addAttribute("purchaseDetail", purchaseDetail);
		System.out.println(purchaseDetail.getPurchaseDate());
		model.addAttribute("userId", userId);
		return "purchase/view";
	}
}
