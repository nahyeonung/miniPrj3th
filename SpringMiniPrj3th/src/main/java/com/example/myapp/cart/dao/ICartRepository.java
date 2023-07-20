package com.example.myapp.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.cart.model.Cart;

@Repository
@Mapper
public interface ICartRepository {
	List<Cart> selectCartList(String userId);
	void insertCart(Cart cart);
	void updateCart(Cart cart);
}
