package com.example.myapp.user.service;

import java.util.List;

import com.example.myapp.user.model.User;

public interface IUserService {
	void insertUser(User user);

	User selectUser(String userId);

	List<User> selectAllUsers();

	void updateUser(User user);

	void deleteUser(User user);

	String getPassword(String userId);

	User selectUserByEmail(String userEmail);
	
}
