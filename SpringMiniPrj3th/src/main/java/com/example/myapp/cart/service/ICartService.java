package com.example.myapp.cart.service;

import java.util.List;

import com.example.myapp.cart.model.Cart;

public interface ICartService {
	List<Cart> selectCartList(String userId);
	void insertCart(Cart cart);
	void updateCart(Cart cart);
}
