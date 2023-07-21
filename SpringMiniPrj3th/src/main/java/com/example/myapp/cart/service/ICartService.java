package com.example.myapp.cart.service;

import java.util.List;

import com.example.myapp.cart.model.Cart;

public interface ICartService {
	List<Cart> selectCartList(String userId);
	Cart selectCart(Cart cart);
	void insertCart(Cart cart);
	void updateCart(Cart cart);
	void deleteCart(int cartId);
}
