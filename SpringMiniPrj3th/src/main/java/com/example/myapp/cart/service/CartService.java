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
	
	@Override
	public Cart selectCart(Cart cart) {
		return cartRepository.selectCart(cart);
	}



	@Override
	public void insertCart(Cart cart) {
		cartRepository.insertCart(cart);
	}

	@Override
	public void updateCart(Cart cart) {
		cartRepository.updateCart(cart);
	}

	@Override
	public void deleteCart(int cartId) {
		cartRepository.deleteCart(cartId);
	}
}
