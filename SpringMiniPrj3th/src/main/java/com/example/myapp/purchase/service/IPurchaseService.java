package com.example.myapp.purchase.service;

import java.util.List;

import com.example.myapp.purchase.model.Purchase;

public interface IPurchaseService {
	void insertPurchase(Purchase purchase);
	void insertPurchaseDetail(Purchase purchase);
	
	Purchase selectUserInfo(String userId);
	Purchase selectProductInfo(Purchase purchase);
	List<Purchase> selectCartInfo(List<Integer> cartIdList, String UserId);

	int getPurchaseId();
	
	List<Purchase> selectPurchaseList(String userId);
	
	Purchase selectPurchaseUserDetail(int purchaseId);
	
	List<Purchase> selectPurchaseProductDetail(int purchaseId);
}
