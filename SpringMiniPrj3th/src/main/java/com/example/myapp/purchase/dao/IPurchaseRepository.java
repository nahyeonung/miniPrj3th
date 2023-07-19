package com.example.myapp.purchase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.purchase.model.Purchase;
import com.example.myapp.purchase.model.PurchaseDetail;

@Repository
@Mapper
public interface IPurchaseRepository {
	void insertAllPurchase(Purchase purchase, PurchaseDetail purchaseDetail);
	void insertPurchase(Purchase purhcase);
	void insertPurchaseDetail(PurchaseDetail purchaseDetail);
	
	List<Purchase> selectAllPurchase(String userId);
	PurchaseDetail selectPurchase(@Param("purchaseId")int purchaseId, @Param("userId")String userId);
}
