package com.example.myapp.user.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.myapp.user.model.User;
import com.example.myapp.user.service.IUserService;
import com.example.myapp.user.service.MailSendService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@Validated
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Autowired
//	UserValidator userValidator;

	@Autowired
	IUserService userService;

	@Autowired
	MailSendService mailSendService;

//	@InitBinder
//	private void initBinder(WebDataBinder binder) {
//		binder.setValidator(userValidator);
//	}


	@RequestMapping(value = "/user/insert", method = RequestMethod.GET)
	public String insertUser(Model model) {
		model.addAttribute("user", new User());
		return "user/join";
	}

	@RequestMapping(value = "/user/insert", method = RequestMethod.POST)
	public String insertUser(@Validated User user, BindingResult result, HttpSession session, Model model) {
//		userValidator.validate(user, result);
//
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/join";
		}

		try {
			user.setUserState(0);
			userService.insertUser(user);
		} catch (DuplicateKeyException e) {
			model.addAttribute("user", user);
			model.addAttribute("message", "이미 존재하는 아이디입니다.");
			return "user/join";
		}

		return "user/login";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.GET)
	public String login(HttpSession session, Model model) {
		System.out.println(session.getAttribute("userId"));
		if(session.getAttribute("userId") == null) {	
		}else {
			String id = (String)session.getAttribute("userId"); 
			User user = userService.selectUser(id); 
			if (user != null) { 
				logger.info(user.toString());
				model.addAttribute("user", user); 
				}
		}
		return "user/login";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public String login(String userId, String userPwd, HttpSession session, Model model) {
		User user = userService.selectUser(userId);
		if (user != null) {
			logger.info(user.toString());
			String dbPassword = user.getUserPwd();
			if (dbPassword.equals(userPwd)) { // 비밀번호 일치
				session.setMaxInactiveInterval(600); // 10분
				session.setAttribute("userId", userId);
				session.setAttribute("userName", user.getUserName());
				session.setAttribute("userState", user.getUserState());
				model.addAttribute("user", user);
			} else { // 비밀번호가 다름
				session.invalidate();
				model.addAttribute("message", "비밀번호가 다릅니다.");
			}
		} else { // 아이디가 없음
			session.invalidate();
			model.addAttribute("message", "없는 아이디입니다.");
		}
		return "user/login";
	}

	@RequestMapping(value = "/user/userInfo", method = RequestMethod.GET)
	public String userInfo(String userId, HttpSession session, Model model) {
		User user = userService.selectUser(userId);
		if (user != null) {
			session.setAttribute("user", user);
			model.addAttribute("user", user);
		}
		return "user/userInfo";
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletRequest request) {
		session.invalidate(); // 로그아웃
		return "index";
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.GET)
	public String updateUser(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		if (userId != null && !userId.equals("")) {
			User user = userService.selectUser(userId);
			model.addAttribute("user", user);
			return "user/update";
		} else {
			// userId가 세션에 없을 때 (로그인하지 않았을 때)
			model.addAttribute("message", "로그인 하지 않은 사용자입니다.");
			return "user/login";
		}
	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public String updateUser(@Validated User user, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "user/update";
		}
		try {
			userService.updateUser(user);
			model.addAttribute("message", "회원 정보가 수정됐습니다.");
			model.addAttribute("user", user);
			return "user/login";
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "user/error";
		}
	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public String deleteUser(HttpSession session, Model model) {
		String userId = (String) session.getAttribute("userId");
		if (userId != null && !userId.equals("")) {
			User user = userService.selectUser(userId);
			model.addAttribute("user", user);
			model.addAttribute("message", "비밀번호 확인");
			return "user/delete";
		} else {
			// userId가 세션에 없을 때 (로그인 하지 않았을 때)
			model.addAttribute("message", "로그인 하지 않은 사용자입니다.");
			return "user/login";
		}
	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.POST)
	public String deleteUser(String userPwd, HttpSession session, Model model) {
		try {
			User user = new User();
			user.setUserId((String) session.getAttribute("userId"));
			user.setUserPwd(userPwd); // userPwd 속성만 설정

			// 변경된 deleteUser 메서드를 사용하여 삭제 수행
			userService.deleteUser(user);

			session.invalidate(); // 사용자 삭제 후 로그아웃 처리
			return "user/login";
		} catch (Exception e) {
			model.addAttribute("message", "회원정보 삭제를 실패하였습니다.");
			e.printStackTrace();
			return "user/delete";
		}
	}

	@RequestMapping(value = "/api/mailcheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> mailCheck(@RequestBody HashMap<String, Object> user) {
		String userEmail = (String) user.get("username");

		// 데이터베이스에서 이메일이 이미 존재하는지 확인
		User existingUser = userService.selectUserByEmail(userEmail);
		if (existingUser != null) {
			// 이미 존재하는 이메일인 경우 에러 응답을 반환
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 이메일입니다.");
		}

		// 새로운 이메일 등록을 위한 이메일 인증 과정 진행
		String authNum = mailSendService.joinEmail(userEmail);

		logger.info("email: " + user.get("userEmail"));
		logger.info("checkNum: " + authNum);
		return ResponseEntity.status(HttpStatus.OK).body(authNum);
	}

	@RequestMapping(value = "/user/findPwd", method = RequestMethod.GET)
	public String findPwd() {
		return "user/findPwd";
	}

	@RequestMapping(value = "/user/sendTemporaryPassword", method = RequestMethod.POST)
	public String sendTemporaryPassword(String userEmail, HttpSession session, Model model) {
		try {
			String temporaryPassword = mailSendService.makeTempPassword();

			// 이메일 발송
			String title = "임시 비밀번호 발송";
			String message = "임시 비밀번호는 " + temporaryPassword + " 입니다. 로그인 후 반드시 비밀번호를 변경해주세요.";
			mailSendService.mailSend(message, userEmail, title);

			// 회원 정보 업데이트
			User user = userService.selectUserByEmail(userEmail);
			if (user != null) {
				// 비밀번호를 임시 비밀번호로 업데이트
				user.setUserPwd(temporaryPassword);
				userService.updateUser(user);
				model.addAttribute("message", "임시 비밀번호가 발송되었습니다. 이메일을 확인해주세요.");
			} else {
				model.addAttribute("message", "이메일에 해당하는 사용자가 없습니다.");
			}
		} catch (Exception e) {
			model.addAttribute("message", "임시 비밀번호 발송 및 회원 정보 업데이트에 실패하였습니다.");
			e.printStackTrace();
		}
		return "user/login"; // 임시 비밀번호 발송 후 로그인 페이지로 이동하도록 설정
	}
}
