package com.example.myapp.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.product.dao.IProductRepository;
import com.example.myapp.product.model.Category;
import com.example.myapp.product.model.Product;
import com.example.myapp.product.model.UploadProduct;

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

	@Override
	public List<Category> selectAllCategory() {
		return productRepository.selectAllCategory();
	}

	@Override
	public int deleteCategory(int categoryId) {
		return productRepository.deleteCategory(categoryId);
	}
	
	@Transactional
	public void insertProducts(UploadProduct product) {
		int row = productRepository.insertProduct(product);
		if(row != 0) {
			int productId = productRepository.selectProductId();
			int rowNum = productRepository.insertProductImg(product, productId);
		}
	}
}
