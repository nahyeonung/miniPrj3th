package com.example.myapp.product.service;

import java.util.List;

import com.example.myapp.product.model.Product;

public interface IProductService {
	List<Product> selectAllProduct(int categoryId);
	int insertCategory(String categoryName);
}
