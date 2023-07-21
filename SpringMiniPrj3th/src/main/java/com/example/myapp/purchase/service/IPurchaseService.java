package com.example.myapp.purchase.service;

import java.util.List;

import com.example.myapp.purchase.model.Purchase;

public interface IPurchaseService {
	
	Purchase selectUserInfo(String userId);
	Purchase selectProductInfo(Purchase purchase);
	List<Purchase> selectCartInfo(List<Integer> cartIdList, String UserId);
}
