package com.example.myapp.community.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.community.dao.IReplyRepository;
import com.example.myapp.community.model.ReplyVO;
@Service
public class replyService implements IReplyService {

	@Autowired
	IReplyRepository dao;
	// 댓글 조회

	// 댓글 조회
	@Override
	public List<ReplyVO> replyList(int writeId) {
	    return dao.replyList(writeId);
	}

	@Override
	public void replyWrite(ReplyVO vo) {
	    dao.replyWrite(vo);
	}

	@Override
	public void replyModify(ReplyVO vo)  {
		dao.replyModify(vo);
	}

	@Override
	public void replyDelete(int writeId) {
	    dao.replyDelete(writeId);
	}}