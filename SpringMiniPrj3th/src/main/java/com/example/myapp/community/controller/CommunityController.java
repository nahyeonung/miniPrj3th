package com.example.myapp.community.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.community.model.Community;
import com.example.myapp.community.service.ICommunityService;

import jakarta.servlet.http.HttpServletRequest; // tomcat 9이하면 javax.servlet
import jakarta.servlet.http.HttpSession;

@Controller
public class CommunityController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ICommunityService communityService;

	@RequestMapping("/community/list/{page}")
	public String getListByCommunity(@PathVariable int page, HttpSession session, Model model) {
		session.setAttribute("page", page);
		List<Community> communityList = communityService.selectArticleListByCommunity(page);
		model.addAttribute("communityList", communityList);
		int bbsCount = communityService.selectTotalArticleCountByCommunity();
		int totalPage = 0;
		if (bbsCount > 0) {
			totalPage = (int) Math.ceil(bbsCount / 10.0);
		}
		int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
		int nowPageBlock = (int) Math.ceil(page / 10.0);
		int startPage = (nowPageBlock - 1) * 10 + 1;
		int endPage = 0;
		if (totalPage > nowPageBlock * 10) {
			endPage = nowPageBlock * 10;
		} else {
			endPage = totalPage;
		}
		model.addAttribute("bbsCount", bbsCount);
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("nowPage", page);
		model.addAttribute("totalPageBlock", totalPageBlock);
		model.addAttribute("nowPageBlock", nowPageBlock);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "community/list";
	}

	@RequestMapping("/community/list")
	public String getListByCommunity(HttpSession session, Model model) {
		return getListByCommunity(1, session, model);
	}

	@RequestMapping("/community/{writeId}/{page}")
	public String getCommunityDetails(@PathVariable int writeId, @PathVariable int page, Model model) {
		Community community = communityService.selectArticle(writeId);
		if (community != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedWriteDate = sdf.format(community.getWriteDate());
			community.setFormattedWriteDate(formattedWriteDate);
		}
		model.addAttribute("community", community);
		model.addAttribute("page", page);
		logger.info("getCommunityDetails " + community.toString());
		return "community/view";
	}

	@RequestMapping("/community/{writeId}")
	public String getCommunityDetails(@PathVariable int writeId, Model model) {
		return getCommunityDetails(writeId, 1, model);
	}

	@RequestMapping(value = "/community/write", method = RequestMethod.GET)
	public String writeArticle(Model model) {
//		model.addAttribute("community", new Community());
		return "community/write";
	}

	@RequestMapping(value = "/community/write", method = RequestMethod.POST)
	public String writeArticle(Community community, BindingResult results, RedirectAttributes redirectAttrs) {
//		logger.info("/community/write : " + community.toString());
		try {
			community.setUserId("test");
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
//		community.setWriteContent(community.getWriteContent().replaceAll("<br>", "\r\n"));
		model.addAttribute("community", community);
		return "community/update";
	}

	@RequestMapping(value = "/community/update", method = RequestMethod.POST)
	public String updateArticle(Community community, RedirectAttributes redirectAttrs) {
		logger.info("/community/update " + community.toString());
		try {
//			community.setWriteContent(community.getWriteContent().replace("\r\n", "<br>"));
//			community.setWriteTitle(Jsoup.clean(community.getWriteTitle(), Safelist.basic()));
//			community.setWriteContent(Jsoup.clean(community.getWriteContent(), Safelist.basic()));
			communityService.updateArticle(community);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/community/" + community.getWriteId();
	}

	@RequestMapping(value = "/community/delete", method = RequestMethod.GET)
	public String deleteArticle(@RequestParam("writeId") int writeId) {
		communityService.deleteArticleByWriteId(writeId);
		return "redirect:/community/list/1";
	}


	@RequestMapping("/community/search/{page}")
	public String search(@RequestParam(required = false, defaultValue = "") String keyword, @PathVariable int page,
			HttpSession session, Model model) {
		try {
			List<Community> communityList = communityService.searchListByContentKeyword(keyword, page);
			model.addAttribute("communityList", communityList);
			int bbsCount = communityService.selectTotalArticleCountByKeyword(keyword);
			int totalPage = 0;
			if (bbsCount > 0) {
				totalPage = (int) Math.ceil(bbsCount / 10.0);
			}
			int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
			int nowPageBlock = (int) Math.ceil(page / 10.0);
			int startPage = (nowPageBlock - 1) * 10 + 1;
			int endPage = 0;
			if (totalPage > nowPageBlock * 10) {
				endPage = nowPageBlock * 10;
			} else {
				endPage = totalPage;
			}
			model.addAttribute("keyword", keyword);
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("nowPage", page);
			model.addAttribute("totalPageBlock", totalPageBlock);
			model.addAttribute("nowPageBlock", nowPageBlock);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "community/search";
	}

	@RequestMapping("/community/search")
	public String search(@RequestParam(required = false, defaultValue = "") String keyword, HttpSession session,
			Model model) {
		return search(keyword, 1, session, model);
	}
	
	
	@RequestMapping("/community/mylist/{page}")
	public String mylist(@RequestParam(required = false, defaultValue = "") String userId, @PathVariable int page,
			HttpSession session, Model model) {
		try {
			List<Community> communityList = communityService.searchListByContentmylist(userId, page);
			model.addAttribute("communityList", communityList);
			int bbsCount = communityService.selectTotalArticleCountBymylist(userId);
			int totalPage = 0;
			if (bbsCount > 0) {
				totalPage = (int) Math.ceil(bbsCount / 10.0);
			}
			int totalPageBlock = (int) (Math.ceil(totalPage / 10.0));
			int nowPageBlock = (int) Math.ceil(page / 10.0);
			int startPage = (nowPageBlock - 1) * 10 + 1;
			int endPage = 0;
			if (totalPage > nowPageBlock * 10) {
				endPage = nowPageBlock * 10;
			} else {
				endPage = totalPage;
			}
			model.addAttribute("userId", userId);
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("nowPage", page);
			model.addAttribute("totalPageBlock", totalPageBlock);
			model.addAttribute("nowPageBlock", nowPageBlock);
			model.addAttribute("startPage", startPage);
			model.addAttribute("endPage", endPage);
			 model.addAttribute("bbsCount", bbsCount); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "community/mylist";
	}

	@RequestMapping("/community/mylist")
	public String mylist(@RequestParam(required = false, defaultValue = "") String userId, HttpSession session,
			Model model) {
		return mylist(userId, 1, session, model);
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