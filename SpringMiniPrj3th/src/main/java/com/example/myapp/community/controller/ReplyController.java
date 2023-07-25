package com.example.myapp.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.community.model.ReplyVO;
import com.example.myapp.community.service.IReplyService;

@Controller
public class ReplyController {

	@Autowired
	IReplyService replyService;
	
	// 댓글 작성
	@RequestMapping(value = "/reply/write/{writeId}", method = RequestMethod.GET)
	public String replyWrite(ReplyVO vo, BindingResult results, RedirectAttributes redirectAttrs, @PathVariable int writeId) {
	    replyService.replyWrite(vo);
	    	vo.setUserId("test");
	    return "/reply/write";
	}
	
	@RequestMapping(value = "/reply/write", method = RequestMethod.POST)
	public String replyWrite(ReplyVO vo, RedirectAttributes redirectAttrs) {
	    replyService.replyWrite(vo);
	    
	    return "redirect:/community/" + vo.getWriteId();
	}

}