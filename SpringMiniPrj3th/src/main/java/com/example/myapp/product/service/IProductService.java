package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadProduct;

public interface IProductService {
	void insertProducts(UploadProduct product);
	
	List<Product> selectAllProduct(int categoryId);
	
	int insertCategory(String categoryName);
	
	List<Category> selectAllCategory();
	
	int deleteCategory(int categoryId);
	
}
