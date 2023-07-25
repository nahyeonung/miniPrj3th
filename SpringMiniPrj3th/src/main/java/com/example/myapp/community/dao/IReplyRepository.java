package com.example.myapp.community.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.myapp.community.model.ReplyVO;


@Repository
@Mapper
public interface IReplyRepository {

	// 댓글 조회
		List<ReplyVO> replyList(int writeId);

		// 댓글 조회
		void replyWrite(ReplyVO vo);

		// 댓글 수정
		void replyModify(ReplyVO vo);

		// 댓글 삭제
		 void replyDelete(int writeId);
}
