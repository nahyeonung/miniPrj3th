package com.example.myapp.user.service;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailSendService implements IMailSendService {

	private final JavaMailSender javaMailSender;
	private int authNumber;

	public MailSendService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void makeRandomNumber() {
		Random random = new Random();
		int checkNum = random.nextInt(888888) + 111111;
		authNumber = checkNum;
	}

	// 가입 인증 이메일
	@Override
	public String joinEmail(String userEmail) {
		makeRandomNumber();

		String toMail = userEmail;
		String title = "회원가입을 위한 인증메일입니다.";
		String message = "홈페이지를 방문해주셔서 감사합니다." + "<br><br>" + "인증번호는 " + authNumber + " 입니다." + "<br><br>"
				+ "해당 인증번호를 인증번호 확인란에 기입하여 주시기 바랍니다.";
		mailSend(message, toMail, title);
		return Integer.toString(authNumber);
	}

	@Override
	public void mailSend(String message, String toMail, String title) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(message, true); // true 입력시 HTML 양식으로 전달됨. 안하면 일단 텍스트 형식임.
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	// 랜덤함수로 임시비밀번호 구문 만들기
	@Override
	public String makeTempPassword() {
	    char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	    SecureRandom random = new SecureRandom();
	    StringBuilder strBuilder = new StringBuilder();

	    // 랜덤한 숫자 하나 추가
	    strBuilder.append(charSet[random.nextInt(10)]);

	    // 랜덤한 소문자 하나 추가
	    strBuilder.append(Character.toLowerCase(charSet[10 + random.nextInt(26)]));

	    // 랜덤한 대문자 하나 추가
	    strBuilder.append(charSet[10 + random.nextInt(26)]);

	    // 나머지 문자들 추가하여 길이가 6이 될 때까지
	    for (int i = 0; i < 3; i++) {
	        int idx = random.nextInt(charSet.length);
	        strBuilder.append(charSet[idx]);
	    }

	    // 문자들을 랜덤하게 섞음
	    for (int i = strBuilder.length() - 1; i > 0; i--) {
	        int j = random.nextInt(i + 1);
	        char temp = strBuilder.charAt(i);
	        strBuilder.setCharAt(i, strBuilder.charAt(j));
	        strBuilder.setCharAt(j, temp);
	    }

	    return strBuilder.toString();
	}
}
