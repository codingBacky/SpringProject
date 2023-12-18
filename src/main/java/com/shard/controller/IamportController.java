package com.shard.controller;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@RestController
public class IamportController {

	// iamport를 이용하여 결제하기를 완료하였을때
	// 처음 요청했던 금액과 결제과 올바르게 이루어졌는지에 대해 아임 포트 서버로 아임포트 거래 고유번호(imp_uid)나 주문
	// 고유번호(merchant_uid)를 보내 확인하는 과정
	// 자바스크립트 형태로 제공하고 있어, 결제 금액 및 결제 상태에 대한 변조가 가능
	@ResponseBody
	@RequestMapping(value = "/verifyIamport/{imp_uid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public IamportResponse<Payment> paymentByImpUid(Model model, Locale locale, HttpSession session,
			@PathVariable(value = "imp_uid") String imp_uid) throws IamportResponseException, IOException {
		IamportClient client = new IamportClient("3214123007006164",
				"JSPBF6yDYVU0kGIOG1Pua25XziNyWYbU0hLvC8ido1yjHxV4i0qTqP9eiVF0wpfM0we19oz0XHtnY5vL");
		return client.paymentByImpUid(imp_uid);
	}
}