package com.example.myapp.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.community.model.Community;

@Repository
@Mapper
public interface ICommunityRepository {
	int selectMaxArticleNo();
	int selectMaxFileId();
	
	void insertArticle(Community community);
	
	List<Community> selectArticleListByCommunity();
	
	Community selectArticle(int writeId);
		//Community>getCommunityDetails

//	void updateReplyNumber(@Param("masterId") int masterId, @Param("replyNumber") int replyNumber);
//	void replyArticle(Community writeId);
	
	
	void updateArticle(Community community);
	
	void deleteArticleByWriteId(int writeId);
//	int selectTotalArticleCount();
//	int selectTotalArticleCountByCommunity(int writeId);

//	int selectTotalArticleCountByKeyword(String keyword);
	List<Community> searchListByContentKeyword(@Param("keyword") String keyword);
}
