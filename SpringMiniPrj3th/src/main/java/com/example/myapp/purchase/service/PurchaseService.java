package com.example.myapp.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.purchase.dao.IPurchaseRepository;
import com.example.myapp.purchase.model.Purchase;

@Service
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	IPurchaseRepository purchaseRepository;

	@Override
	public Purchase selectUserInfo(String userId) {
		return purchaseRepository.selectUserInfo(userId);
	}

	@Override
	public Purchase selectProductInfo(Purchase purchase) {
		return purchaseRepository.selectProductInfo(purchase);
	}

	@Override
	public List<Purchase> selectCartInfo(List<Integer> cartIdList, String UserId) {
		return purchaseRepository.selectCartInfo(cartIdList, UserId);
	}	
}
