package com.example.myapp.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.product.dao.IProductRepository;

@Service
public class productService implements IProductService{

	@Autowired
	IProductRepository productRepository;
	
	@Override
	public int selectCnt() {
		return productRepository.selectCnt();
	}

}
