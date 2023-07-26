package com.example.myapp.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.community.dao.ICommunityRepository;
import com.example.myapp.community.dao.IReplyRepository;
import com.example.myapp.community.model.ReplyVO;
@Service
public class ReplyService implements IReplyService {

	@Autowired
	IReplyRepository dao;
	// 댓글 조회
	@Autowired
	ICommunityRepository communityRepository;
	// 댓글 조회
	@Override
	public List<ReplyVO> replyList(int writeId) {
	    return dao.replyList(writeId);
	}

	@Override
	public void replyWrite(ReplyVO vo) {
		communityRepository.updateReplyCnt(vo.getWriteId(), 1);
	    dao.replyWrite(vo);
	}

	@Override
	public void replyModify(ReplyVO vo)  {
		dao.replyModify(vo);
	}

	@Override
	public void replyDelete(int writeId) {
	    dao.replyDelete(writeId);
	}
	


}