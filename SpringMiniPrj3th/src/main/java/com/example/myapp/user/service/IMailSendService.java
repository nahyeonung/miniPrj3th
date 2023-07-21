package com.example.myapp.user.service;

public interface IMailSendService {
	void makeRandomNumber();

	String joinEmail(String email);

	void mailSend(String message, String email, String title);
}