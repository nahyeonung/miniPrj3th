package com.example.myapp.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.cart.dao.ICartRepository;
import com.example.myapp.cart.model.Cart;

@Service
public class CartService implements ICartService {

	@Autowired
	ICartRepository cartRepository;
	
	@Override
	public List<Cart> selectCartList(String userId) {
		return cartRepository.selectCartList(userId);
	}

}
