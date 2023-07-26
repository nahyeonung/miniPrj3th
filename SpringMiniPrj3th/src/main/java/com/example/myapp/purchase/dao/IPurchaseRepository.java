package com.example.myapp.purchase.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.purchase.model.Purchase;

@Repository
@Mapper
public interface IPurchaseRepository {
	void insertPurchase(Purchase purchase);
	void insertPurchaseDetail(Purchase purchase);
	
	Purchase selectUserInfo(String userId);
	Purchase selectProductInfo(int productId);
	List<Purchase> selectCartInfo(@Param("cartIdList") List<Integer> cartIdList, @Param("userId") String userId);
	
	int getPurchaseId();
	
	List<Purchase> selectPurchaseList(String userId);
	
	Purchase selectPurchaseUserDetail(int purchaseId);
	
	List<Purchase> selectPurchaseProductDetail(int purchaseId);
	
	List<Purchase> selectTopThree();
}
