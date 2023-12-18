package com.shard.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MailSendService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	private int authNumber;
		
	public void makeRandomNumber() {
		// 난수의 범위 111111 ~ 999999 (6자리 난수)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNumber = checkNum;
	}
	
	public String joinEmail(String email) {
		makeRandomNumber();
		String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
		String toMail = email;
		String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목 
		String content = 
				"홈페이지를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 ! 
                "<br><br>" + 
			    "인증 번호는 " + authNumber + "입니다." + 
			    "<br>" + 
			    "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}
	
	public String findPwd(String email) {
		makeRandomNumber();
		String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
		String toMail = email;
		String title = "비밀번호 찾기 이메일입니다."; // 이메일 제목 
		String content = 
				"홈페이지를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 ! 
						"<br><br>" + 
						"인증 번호는 " + authNumber + "입니다." + 
						"<br>" + 
						"해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}
	
	public void enwserEmail(String email) {
		String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
		String toMail = email;
		String title = "문의 답변 완료 메세지입니다."; // 이메일 제목 
		String content = 
				"등록하신 문의에 답변이 등록되었습니다." + 	//html 형식으로 작성 ! 
						"<br><br>" + 
						"지금 답변을 확인하실 수 있습니다." + 
						"<br>" + 
						"앞으로 더 나은 서비스를 위해 노력하겠습니다"; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
	}
	
	public void arriveEmail(String email) {
		String setFrom = ".com"; // email-config에 설정한 자신의 이메일 주소를 입력 
		String toMail = email;
		String title = "주문하신 상품이 도착하였습니다."; // 이메일 제목 
		String content = 
				"주문하신 상품이 안전히 고객의 집으로 발송되었습니다" + 	//html 형식으로 작성 ! 
						"<br><br>" + 
						"제품을 확인하시어 문제가 있으시면 연락바랍니다." + 
						"<br>" + 
						"앞으로 더 나은 서비스를 위해 노력하겠습니다. 감사합니다"; //이메일 내용 삽입
		mailSend(setFrom, toMail, title, content);
	}
	
	//이메일 전송 메소드
	public void mailSend(String setFrom, String toMail, String title, String content) { 
		MimeMessage message = mailSender.createMimeMessage();
		// true 매개값을 전달하면 multipart 형식의 메세지 전달이 가능.문자 인코딩 설정도 가능하다.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText(content,true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
