package com.ChattingApp.Service;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	private final Random rand = new Random();
	private final int MAX = 999999;
	private final int MIN = 100000;
	private String SEND_EMAIL_FROM = "chitchattersservices@gmail.com";
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
		
	}
	
	public int verificationCodeEmailSender(String email, int code) {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		
		
		final String VERIFY_EMAIL_TEXT = "Hello;"+"\nThank you for signing up with Chit-Chatters."
				+ "To complete the registration process, please use the following verification code:"
				+ "\nVerification Code: "+code+"\n\nChit-Chatting Team.";
		
		final String VERIFY_EMAIL_SUBJECT = "Verify your email";
		
		
		prepareEmailContent(mimeMessageHelper, mimeMessage, email, VERIFY_EMAIL_TEXT, VERIFY_EMAIL_SUBJECT);
		javaMailSender.send(mimeMessage);
		
		return code;
	}

	public int generateVerificationCode() {
		int code = rand.nextInt(MAX - MIN+ 1) + MIN;
		return code;
	}
	
	private void prepareEmailContent(MimeMessageHelper mimeMessageHelper, MimeMessage mimeMessage, String email, 
			String VERIFY_EMAIL_TEXT, String VERIFY_EMAIL_SUBJECT) {
		
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(SEND_EMAIL_FROM );
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setText(VERIFY_EMAIL_TEXT);
			mimeMessageHelper.setSubject(VERIFY_EMAIL_SUBJECT);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
}
