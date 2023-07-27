package com.example.myapp.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myapp.community.model.ReplyVO;
import com.example.myapp.community.service.IReplyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReplyController {

	@Autowired
	IReplyService replyService;

//	// 댓글 작성
//	@RequestMapping(value = "/reply/write/{writeId}", method = RequestMethod.GET)
//	public String replyWrite(ReplyVO vo, BindingResult results, RedirectAttributes redirectAttrs, @PathVariable int writeId, Model model) {
//		
//		replyService.replyWrite(vo);
//		model.addAttribute("vo", vo);
//		model.addAttribute("writeId", writeId);
//	    return "/reply/write";
//	}
//	
//	@RequestMapping(value = "/reply/write", method = RequestMethod.POST)
//	public String replyWrite(ReplyVO vo, RedirectAttributes redirectAttrs) {
//	    replyService.replyWrite(vo);
//	    vo.setUserId("test");
//	    return "redirect:/community/" + vo.getWriteId();
//	}

	@PostMapping("/reply/write")
	public String replyWrite(@RequestParam("writeId") int writeId, HttpSession session, @RequestParam("replyContent") String replyContent) {
		String userId = (String)session.getAttribute("userId");
		if(userId != null && !userId.equals("")) {
		ReplyVO replyVo = new ReplyVO();
		replyVo.setReplyContent(replyContent);
		replyVo.setWriteId(writeId);
		replyVo.setUserId(userId);
		replyService.replyWrite(replyVo);
		 return "redirect:/community/" + writeId;
	}else {
		return "user/login";
	}}
	
	
}