package com.example.myapp.community.service;

import java.util.List;

import com.example.myapp.community.model.Community;

public interface ICommunityService {
	void insertArticle(Community community);


	List<Community> selectArticleListByCommunity(int page);

	Community selectArticle(int writeId);


//	void replyArticle(Community community);

	
	void updateArticle(Community community);
//	Community selectDeleteArticle(int writeId);
	void deleteArticleByWriteId(int writeId);

	int selectTotalArticleCountByCommunity();
	int selectTotalArticleCountByKeyword(String keyword);
	List<Community> searchListByContentKeyword(String keyword, int page);
	
	int selectTotalArticleCountBymylist(String userId);
	List<Community> searchListByContentmylist(String userId, int page);

}