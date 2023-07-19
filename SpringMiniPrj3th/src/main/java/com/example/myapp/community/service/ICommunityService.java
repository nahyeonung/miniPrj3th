package com.example.myapp.community.service;

import java.util.List;

import com.example.myapp.community.model.Community;

public interface ICommunityService {
	void insertArticle(Community community);


	List<Community> selectArticleListByCommunity();

	Community selectArticle(int writeId);


//	void replyArticle(Community community);


	void updateArticle(Community community);
	
	void deleteArticle(int writeId);

	

	List<Community> searchListByContentKeyword(String keyword);

}