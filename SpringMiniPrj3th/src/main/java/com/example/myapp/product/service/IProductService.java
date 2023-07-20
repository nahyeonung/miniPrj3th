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
	
	int insertCategory(String categoryName);
	
	List<Category> selectAllCategory();
	
	int deleteCategory(int categoryId);
	
	UploadImage getFile(int imgId);
	
	int deleteProduct(int productId);
	
	int deleteBackProduct(int productId);
	
	Product selectProdcut(int productId);
}
