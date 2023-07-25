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
	
	void insertArticle(Community community);
	
	List<Community> selectArticleListByCommunity(@Param("start") int start, @Param("end") int end);
	
	Community selectArticle(int writeId);
		//Community>getCommunityDetails

//	void updateReplyNumber(@Param("masterId") int masterId, @Param("replyNumber") int replyNumber);
//	void replyArticle(Community writeId);
	
	
	void updateArticle(Community community);
//	Community selectDeleteArticle(int writeId);
	void deleteArticleByWriteId(int writeId);
	int selectTotalArticleCountByCommunity();
	int selectTotalArticleCountByKeyword(String keyword);
	List<Community> searchListByContentKeyword(@Param("keyword") String keyword, @Param("start") int start, @Param("end") int end);
	
	int selectTotalArticleCountBymylist(String userId);
	List<Community> searchListByContentmylist(@Param("userId") String userId, @Param("start") int start, @Param("end") int end);
	
}
