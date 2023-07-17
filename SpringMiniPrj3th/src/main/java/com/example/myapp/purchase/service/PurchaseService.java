package com.example.myapp.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.purchase.dao.IPurchaseRepository;
import com.example.myapp.purchase.model.Purchase;
import com.example.myapp.purchase.model.PurchaseDetail;

@Service
public class PurchaseService implements IPurchaseService{
	
	@Autowired
	IPurchaseRepository purchaseRepository;
	
	
	@Override
	public void insertPurchase(Purchase purhcase) {
		purchaseRepository.insertPurchase(purhcase);
	}


	@Override
	public void insertPurchaseDetail(PurchaseDetail purchaseDetail) {
		purchaseRepository.insertPurchaseDetail(purchaseDetail);
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
