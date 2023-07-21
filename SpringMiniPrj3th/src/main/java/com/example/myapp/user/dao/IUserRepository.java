package com.example.myapp.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.user.model.User;

@Repository
@Mapper
public interface IUserRepository {
	void insertUser(User user);

	User selectUser(String userId);

	List<User> selectAllUsers();

	void updateUser(User user);

	void deleteUser(User user);

	String getPassword(String userId);
	
}
