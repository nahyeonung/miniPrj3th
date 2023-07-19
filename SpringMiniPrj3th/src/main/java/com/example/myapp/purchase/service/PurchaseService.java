package com.example.myapp.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.purchase.dao.IPurchaseRepository;
import com.example.myapp.purchase.model.Purchase;
import com.example.myapp.purchase.model.PurchaseDetail;

@Service
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	IPurchaseRepository purchaseRepository;
	
	@Transactional
	public void insertAllPurchase(Purchase purchase, PurchaseDetail purchaseDeail) {
		purchaseRepository.insertPurchase(purchase);
		purchaseRepository.insertPurchaseDetail(purchaseDeail);
	}

	@Override
	public List<Purchase> selectAllPurchase(String userId) {
		return purchaseRepository.selectAllPurchase(userId);
	}


	@Override
	public PurchaseDetail selectPurchase(int purchaseId, String userId) {
		return purchaseRepository.selectPurchase(purchaseId, userId);
	}
	
	
	
}
