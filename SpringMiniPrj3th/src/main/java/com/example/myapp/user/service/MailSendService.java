package com.example.myapp.user.service;

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

}
