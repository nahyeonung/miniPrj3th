package com.example.myapp.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.user.dao.IUserRepository;
import com.example.myapp.user.model.User;


@Service
public class UserService implements IUserService {

	@Autowired
	IUserRepository userDao;
	
	@Override
	public void insertUser(User user) {
		userDao.insertUser(user);
	}

	@Override
	public User selectUser(String userid) {
		return userDao.selectUser(userid);
	}

	@Override
	public List<User> selectAllUsers() {
		return userDao.selectAllUsers();
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	@Override
	public String getPassword(String userid) {
		return userDao.getPassword(userid);
	}

}