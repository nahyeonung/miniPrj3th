package com.example.myapp.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.product.dao.IProductRepository;
import com.example.myapp.product.model.Product;

@Service
public class productService implements IProductService{

	@Autowired
	IProductRepository productRepository;
	
	@Override
	public List<Product> selectAllProduct(int categoryId) {
		return productRepository.selectAllProduct(categoryId);
	}

	@Override
	public int insertCategory(String categoryName) {
		return productRepository.insertCategory(categoryName);
	}

}
