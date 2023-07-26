package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadImage;
import com.example.myapp.product.model.UploadProduct;

public interface IProductService {
	void insertProducts(UploadProduct product);
	
	void insertProducts(UploadProduct product, UploadImage img);
	
	int updateProduct(UploadProduct product);
	
	int updateProduct(UploadProduct product, UploadImage img);
	
	List<Product> selectAllProduct(int categoryId);
	
	List<Product> selectPagingProduct(int categoryId, int page);
	
	int insertCategory(String categoryName);
	
	List<Category> selectAllCategory();
	
	int deleteCategory(int categoryId);
	
	UploadImage getFile(int imgId);
	
	int deleteProduct(int productId);
	
	int deleteBackProduct(int productId);
	
	Product selectProduct(int productId);
	
	int checkCategory(String categoryName);
	
	int selectCountCategory();
	
	List<Category> selectPagingCategory(int min, int max);

	int selectCountUseProduct(int categoryId);
}

