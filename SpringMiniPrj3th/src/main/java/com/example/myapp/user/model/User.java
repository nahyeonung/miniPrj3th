package com.example.myapp.user.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class User {
	private String userId;
	private String userPwd;
	private String userName;
	private Date userBirth;
	private String userAddress;
	private String userPhone;
	private int userState;

}
