package com.example.myapp.community.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.community.model.Community;
import com.example.myapp.community.service.ICommunityService;
import com.example.myapp.review.model.ReviewImage;

import jakarta.servlet.http.HttpServletRequest; // tomcat 9이하면 javax.servlet
import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ICommunityService communityService;

	@RequestMapping("/community/list")
	public String getListByCommunity(Model model) {
		List<Community> communityList = communityService.selectArticleListByCommunity();
		model.addAttribute("communityList", communityList);
		return "community/list";
	}

	@RequestMapping("/community/{writeId}")
	public String getCommunityDetails(@PathVariable int writeId, Model model) {
		Community community = communityService.selectArticle(writeId);
		model.addAttribute("community", community);
		logger.info("getCommunityDetails " + community.toString());
		return "community/view";
	}

	@RequestMapping(value = "/community/write", method = RequestMethod.GET)
	public String writeArticle(Model model) {
//		model.addAttribute("community", new Community());
		return "community/write";
	}

	@RequestMapping(value = "/community/write" , method=RequestMethod.POST)
	public String writeArticle(Community community, BindingResult results, RedirectAttributes redirectAttrs) {
//		logger.info("/community/write : " + community.toString());
		try {
		community.setUserId("test");
//		community.setWriteContent(community.getWriteContent().replace("\r\n", "<br>"));
//			community.setWriteTitle(Jsoup.clean(community.getWriteTitle(), Safelist.basic()));
//			community.setWriteContent(Jsoup.clean(community.getWriteContent(), Safelist.basic()));
			communityService.insertArticle(community);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", "공란이 있습니다.");
		}
		return "redirect:/community/list";
	}

	@RequestMapping(value = "/community/update/{writeId}", method = RequestMethod.GET)
	public String updateArticle(@PathVariable int writeId, Model model) {
		Community community = communityService.selectArticle(writeId);
		community.setWriteContent(community.getWriteContent().replaceAll("<br>", "\r\n"));
		model.addAttribute("community", community);
		return "community/update";
	}

	@RequestMapping(value = "/community/update", method = RequestMethod.POST)
	public String updateArticle(Community community, RedirectAttributes redirectAttrs) {
		logger.info("/community/update " + community.toString());
		try {
			community.setWriteContent(community.getWriteContent().replace("\r\n", "<br>"));
			community.setWriteTitle(Jsoup.clean(community.getWriteTitle(), Safelist.basic()));
			community.setWriteContent(Jsoup.clean(community.getWriteContent(), Safelist.basic()));
			
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/community/" + community.getWriteId();
	}

//	@RequestMapping(value="/community/delete/{writeId}")
//	public String deleteArticle(@PathVariable int communityId, Model model) {
//		logger.info("/community/delete " + community.toString());
//		model.addAttribute("writeId", writeId);
//		return "community/list";
//	}
//	
	@RequestMapping("/community/search")
	public String search(@RequestParam(required = false, defaultValue = "") String keyword,
			HttpSession session, Model model) {
		try {
			List<Community> communityList = communityService.searchListByContentKeyword(keyword);
			model.addAttribute("communityList", communityList);
			model.addAttribute("keyword", keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "community/search";
	}
//
//	@ExceptionHandler({ RuntimeException.class })
//	public String error(HttpServletRequest request, Exception ex, Model model) {
//		model.addAttribute("exception", ex);
//		model.addAttribute("stackTrace", ex.getStackTrace());
//		model.addAttribute("url", request.getRequestURI());
//		return "error/runtime";
//	}
}