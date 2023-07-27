package com.example.myapp.purchase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.purchase.model.Purchase;
import com.example.myapp.purchase.service.IPurchaseService;
import com.example.myapp.review.model.Review;
import com.example.myapp.review.service.IReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PurchaseController {

	@Autowired
	IPurchaseService purchaseService;
	
	@Autowired
	IReviewService reviewService;
	
	@RequestMapping("/")
	public String index(Model model) {
		List<Purchase> list = purchaseService.selectTopThree();
		List<Review> reviewList = reviewService.selectPopularReview();
		int reviewCnt = reviewList.size();
		model.addAttribute("pTopThree", list);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("reviewCnt", reviewCnt);
		return "index";
	}
		
	
	@RequestMapping(value="/purchase/insert", method=RequestMethod.GET) 
	public String InsertPurchase(@RequestParam List<Integer> cartIdList, Purchase purchase, HttpSession session, Model model) { 
		String userId =	(String)session.getAttribute("userId");
		System.out.println(userId);
		purchase = purchaseService.selectUserInfo(userId);
	
		List<Purchase> list = purchaseService.selectCartInfo(cartIdList, userId);
		model.addAttribute("list", list); model.addAttribute("purchase", purchase);
	
		int sum = 0; 
		int cnt = 0; 
		for (Purchase i : list) { 
			sum += i.getProductPrice() * i.getCartCnt(); cnt++; 
		} 
		model.addAttribute("sum", sum); 
		model.addAttribute("cnt", cnt); 
		return "purchase/insert"; 
	}
	 
	@PostMapping("/purchase/insert")
	public String Insert(@RequestParam List<Integer> productId, @RequestParam List<Integer> purchaseCnt,
		Purchase purchase, HttpSession session) {
		purchase.setUserId((String) session.getAttribute("userId"));
		purchaseService.insertPurchase(purchase);
		
		int purchaseId = purchaseService.getPurchaseId();
		purchase.setPurchaseId(purchaseId);

		for (int i = 0; i < productId.size(); i++) {
			purchase.setProductId(productId.get(i));
			purchase.setPurchaseCnt(purchaseCnt.get(i));
			purchaseService.insertPurchaseDetail(purchase);
			purchaseService.deleteCartPurchase(purchase.getProductId());
		}
		return "redirect:/purchase/list";
	}
	@RequestMapping(value = "/purchase/buy", method = RequestMethod.GET)
	public String InsertBuyPurchase(Purchase purchase, HttpSession session, Model model, int cartCnt) {
		Purchase buy = purchaseService.selectProductInfo(purchase.getProductId());
		int sum = (buy.getProductPrice()* cartCnt);
		buy.setCartCnt(cartCnt);
		model.addAttribute("buy", buy);

		String userId = (String) session.getAttribute("userId");
		if(userId != null && !userId.equals("")) {
		purchase = purchaseService.selectUserInfo(userId);

		model.addAttribute("purchase", purchase);
		model.addAttribute("sum", sum);

		return "purchase/insert";}
		else {
			model.addAttribute("message", "NOT_LOGIN_USER");
			return "user/login";
		}
	}

	@RequestMapping("/purchase/list")
	public String selectAllPurchase(Model model, Purchase purchase, HttpSession session) {
		List<Purchase> purchaseList = purchaseService.selectPurchaseList((String) session.getAttribute("userId"));
		model.addAttribute("purchaseList", purchaseList);
		return "purchase/list";
	}

	@RequestMapping("/purchase/list/{purchaseId}")
	public String selectPurchaseDetail(@PathVariable int purchaseId, Model model, Purchase purchase,
			HttpSession session) {
		purchase.setUserId((String) session.getAttribute("userId"));

		Purchase userInfo = purchaseService.selectPurchaseUserDetail(purchaseId);
		model.addAttribute("userInfo", userInfo);

		List<Purchase> purchaseList = purchaseService.selectPurchaseProductDetail(purchaseId);
		model.addAttribute("purchaseList", purchaseList);

		model.addAttribute("purchaseId", purchaseId);

		return "purchase/detail";
	}
}
