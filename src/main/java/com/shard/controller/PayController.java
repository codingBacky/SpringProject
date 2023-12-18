package com.shard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.PayVO;
import com.shard.service.CartService;
import com.shard.service.OrderService;
import com.shard.service.PayService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/order/*")
@Log4j
public class PayController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private PayService payService;

	
	// 검증완료후 결제완료시 작업
	@PostMapping("/payComplete")
	public String payComplete(  @RequestParam int postcode , 
								@RequestParam String roadAddress,
								@RequestParam String detailAddr,
								@RequestParam String email, 
								@RequestParam int cartNum,
								@RequestParam String itemNum,
								@RequestParam Integer usePoint,
								@RequestParam String addressSave,
								@RequestParam Integer issueNum,
								@RequestParam String cntArray,
								@RequestParam String sizeArray,
								@RequestParam int TotalPriceWithShipping,
								@RequestParam String req,
								@RequestParam String orderName,
								@RequestParam String orderPhone
								
								) throws UnsupportedEncodingException {
		System.out.println(postcode);
		System.out.println(roadAddress);
		System.out.println(detailAddr);
		System.out.println(email);
		System.out.println(cartNum);
		System.out.println(itemNum);
		System.out.println(usePoint);
		System.out.println(addressSave);
		System.out.println(issueNum);
		System.out.println(cntArray);
		System.out.println(sizeArray);
		System.out.println(TotalPriceWithShipping);
		System.out.println(req);
		System.out.println(orderName);
		System.out.println(orderPhone);
		
		if(!"".equals(addressSave) && addressSave != null) {
			DeliverAddrVO davo = DeliverAddrVO.builder()
				.postcode(postcode)
				.roadAddress(roadAddress)
				.detailAddr(detailAddr)
				.addrName(addressSave)
				.email(email)
				.build();
			//배송지 저장 클릭되어 있으면 저장
			orderService.addDeliverAddr(davo, email);
		}

		OrdersVO ovo = OrdersVO.builder()
				.totalPrice(TotalPriceWithShipping)
				.req(req)
				.deliverZipCode(postcode)
				.userDeliverAddr(roadAddress)
				.usePoint(usePoint)
				.email(email)
				.deliverDetailAddr(detailAddr)
				.deliverName(orderName)
				.deliverPhone(orderPhone)
				.orderComplete(1).build();
		
		 // 문자열에서 괄호와 공백 제거
		String transItemNums = itemNum.replaceAll("\\[|\\]|\\s", "");
        // 쉼표로 분리된 숫자 문자열을 리스트로 변환
        List<Integer> itemNums = Arrays.stream(transItemNums.split(","))
                                       .map(Integer::parseInt)
                                       .collect(Collectors.toList());
        log.info("itemNums: " + itemNums);
        // 문자열에서 괄호와 공백 제거
		String transcnts = cntArray.replaceAll("\\[|\\]|\\s", "");
		// 쉼표로 분리된 숫자 문자열을 리스트로 변환
        List<Integer> cnts = Arrays.stream(transcnts.split(","))
                                       .map(Integer::parseInt)
                                       .collect(Collectors.toList());
        
        log.info("cnts: " + cnts);
        // 문자열에서 대괄호와 공백 제거 후 쉼표로 분리된 문자열 배열 얻기
        String[] transcntsSizes = sizeArray.replaceAll("[\"\\[\\]]", "").replaceAll("\\s", "").split(",");
        // 문자열 배열을 List<String>으로 변환
        List<String> sizes = Arrays.asList(transcntsSizes);
        log.info("Sizes: " + sizes);
        //주문생성, orderId반환, 장바구니 아이템삭제
        int orderId = orderService.orderComplete(ovo, itemNums, email);
        
        if(cartNum != 0) {
        orderService.onlyOrderComplete(cartNum, itemNums);
        }
        
        List<DetailOrderVO> dovoList = new ArrayList<>();
        // 세 개의 리스트를 동시에 순회하면서 DetailOrderVO 객체를 생성하여 리스트에 추가
        for (int i = 0; i < itemNums.size(); i++) {
            DetailOrderVO dovo = DetailOrderVO.builder()
                    .itemNum(itemNums.get(i))
                    .cnt(cnts.get(i))
                    .size(sizes.get(i))
                    .build();
            
            orderService.orderComplete2(dovo, orderId);
            dovoList.add(dovo);
        }
        log.info("issueNum >>" + issueNum);
        log.info("usePoint >>" + usePoint);
        orderService.orderComplete3(orderId, issueNum, usePoint, email);
			
        PayVO pvo = PayVO.builder()
        		.orderId(orderId)
        		.email(email)
        		.payTotal(TotalPriceWithShipping)
        		.payMethod("creadit card")
        		.payComplete(1)
        		.build();
        payService.payInsert(pvo);
        
      //세달동안 구매합계
        int totalAmountForLast3Months = orderService.totalAmountForLast3Months(email);
        //멤버십 업데이트(0~20미만 0,3달동안 50만원 이상구매 1, 3달동안 100만원 이상구매 2) 
        int membership = 0;
        if (totalAmountForLast3Months >= 1000000) {
        	membership = 2; // 3달동안 100만원 이상 구매
        } else if (totalAmountForLast3Months >= 500000) {
        	membership = 1; // 3달동안 50만원 이상 구매
        } else {
            membership = 0; // 3달동안 50만원 미만 구매
        }
        orderService.membershipUpdate(membership, email);
       //멤버십, 멤버십의 적립률 and 배송비
      	int memNum = cartService.getMembership(email);
      	int pointRate = cartService.getPointRate(memNum);
        orderService.customerPointPlus((int)(TotalPriceWithShipping*((float)(pointRate)/100)),email);
        
        
        return "/order/orderComplete";
	}

	@GetMapping("/orderComplete")
	@PreAuthorize("isAuthenticated()")
	public String orderComplete() {
		
		return "/order/orderComplete";
	}


}