package com.example.myapp.community.service;

import java.util.List;

import com.example.myapp.community.model.ReplyVO;

public interface IReplyService {


	// 댓글 조회
		List<ReplyVO> replyList(int writeId);

		// 댓글 조회
		 void replyWrite(ReplyVO vo);

		// 댓글 수정
		 void replyModify(ReplyVO vo);

		// 댓글 삭제
		 void replyDelete(int write);
}
