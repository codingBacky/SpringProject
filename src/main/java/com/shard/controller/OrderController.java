package com.shard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.ItemVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.ShardMemberVO;
import com.shard.service.CartService;
import com.shard.service.OrderService;
import com.shard.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/order/*")
@Log4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@PostMapping("/quickCheckOut")
	public String quickOrder(String email, int itemNum, int sale, @RequestParam("cnts") List<Integer> cnts,
			@RequestParam("sizes") List<String> sizes, Model model) {
		log.info(email);
		log.info(itemNum);
		log.info(sale);
		log.info(cnts);
		log.info(sizes);
		// 고객 정보를 기본 정보로 주기 위해 가져옴
		log.info("customer" + userService.getUser(email));
		model.addAttribute("customer", userService.getUser(email));

		// 배송지 정보
		System.out.println("회원 주소" + orderService.getDefaultAddress(email));
		model.addAttribute("deliverAddr", orderService.getDefaultAddress(email));

		// 총 금액
		int totalPrice = orderService.calculateTotalAmount(sale, cnts);
		model.addAttribute("extractCartItemCnts", cnts);
		model.addAttribute("sizes", sizes);
		model.addAttribute("totalPrice", totalPrice);
		log.info("totalPrice >>>" + totalPrice);

		List<Integer> tp = List.of(totalPrice);
		model.addAttribute("totalPriceList", tp);

		List<Integer> ir = List.of(itemNum);
		model.addAttribute("itemNumList", ir);

		// 바로주문할 item 정보
		ItemVO ivo = orderService.getItem(itemNum);
		ivo.setSale(totalPrice);

		List<ItemVO> itemList = List.of(ivo);

		model.addAttribute("itemList", itemList);
		log.info("아이템 정보 >>" + itemList);

		// 멤버십, 멤버십의 적립률 and 배송비
		int memNum = cartService.getMembership(email);
		int pointRate = cartService.getPointRate(memNum);
		int deliveryCharge = cartService.deliveryCharge(memNum);
		log.info("getPointRate view >>" + pointRate);
		log.info("deliveryCharge view >>" + deliveryCharge);
		model.addAttribute("deliveryCharge", deliveryCharge);

		// 총가격
		int TotalPriceWithShipping = deliveryCharge + totalPrice;
		log.info(TotalPriceWithShipping);
		model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);

		// 적립예정 포인트 총합
		int itemRewardPoint = (int) (totalPrice * ((float) (pointRate) / 100));
		log.info("itemRewardPoints >>" + itemRewardPoint);

		List<Integer> rp = List.of(itemRewardPoint);
		log.info("itemRewardPoints >>" + rp);

		model.addAttribute("itemRewardPoints", rp);

		// 발급된 쿠폰중 사용가능한 쿠폰 가져옴
		List<CouponIssuanceVO> cvoList = orderService.getCouponIssuance(email);
		log.info("cvoList >>" + cvoList);
		model.addAttribute("couponList", cvoList);

		// 발급된 쿠폰의 쿠폰 정보를 가져옴
		List<Integer> couponNums = orderService.extractCouponNums(cvoList);
		List<CouponVO> couponInfo = orderService.getCoupon(couponNums);
		log.info("couponNums >>" + couponNums);
		log.info("couponInfo >>" + couponInfo);

		// 사용가능한 쿠폰들을 매칭해 정보 추출
		List<CouponVO> matchedCouponInfoList = orderService.matchedCouponInfoList(couponNums, couponInfo, cvoList);
		log.info("matchedCouponInfoList >>>>>>>>>>>" + matchedCouponInfoList);
		model.addAttribute("matchedCouponInfoList", matchedCouponInfoList);

		// 할인율, 할인금액 중 0이 아닌 숫자 추출
		List<Integer> extractedValues = orderService.extractedValues(couponInfo);
		log.info("extractedValues >>" + extractedValues);

		// 할인 구분
		List<String> discountDivision = orderService.couponDiscountAmount(matchedCouponInfoList);
		model.addAttribute("discountDivision", discountDivision);
		model.addAttribute("cartNum", 0);

		// itemRewardPoints의 총합 -> 총 적립금액 산출
		int totalRewardPoints = (int) (totalPrice * ((float) (pointRate) / 100));
		model.addAttribute("totalRewardPoints", totalRewardPoints);

		return "/order/checkOut";
	}

	@PostMapping("/checkOut")
	public void order(@RequestParam String email, @RequestParam int totalPrice, @RequestParam int cartNum,
			@RequestParam int TotalPriceWithShipping, @RequestParam int deliveryCharge, @RequestParam int pointRate,
			@RequestParam("itemRewardPoints") List<Integer> itemRewardPoints,
			@RequestParam("totalPriceList") List<Integer> totalPriceList,
			@RequestParam("extractCartItemCnts") List<Integer> extractCartItemCnts,
			@RequestParam("sizes") List<String> sizes, @RequestParam("itemNum") List<Integer> itemNum, Model model) {

		log.info("email >>" + email);
		log.info("totalPrice >>" + totalPrice);
		log.info("cartNum >>" + cartNum);
		log.info("TotalPriceWithShipping >>" + TotalPriceWithShipping);
		log.info("deliveryCharge >>" + deliveryCharge);
		log.info("pointRate >>" + pointRate);
		log.info("extractCartItemCnts >>" + extractCartItemCnts);
		log.info("itemNum >>>>>>>>>" + itemNum);
		log.info("totalPriceList >>" + totalPriceList);
		log.info("itemRewardPoints >>" + itemRewardPoints);
		log.info("sizes >>" + sizes);
		log.info("extractCartItemCnts >>" + extractCartItemCnts);

		model.addAttribute("cartNum", cartNum);

		// 고객 정보를 기본 정보로 주기 위해 가져옴
		log.info("customer" + userService.getUser(email));
		model.addAttribute("customer", userService.getUser(email));

		// 배송지 정보
		System.out.println("회원 주소" + orderService.getDefaultAddress(email));
		model.addAttribute("deliverAddr", orderService.getDefaultAddress(email));

		// 총 상품금액, 배송비
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("deliveryCharge", deliveryCharge);

		// 이전페이지에서 선택한 item의 정보들을 가져옴
		List<ItemVO> itemVOList = cartService.getItems(itemNum);
		log.info("itemVOList" + itemVOList);
		model.addAttribute("itemList", itemVOList);
		model.addAttribute("itemNumList", itemNum);
		// 상품 개수, 상품 개수를 곱한 금액, 아이템당 적립금
		model.addAttribute("extractCartItemCnts", extractCartItemCnts);
		model.addAttribute("totalPriceList", totalPriceList);
		model.addAttribute("itemRewardPoints", itemRewardPoints);
		model.addAttribute("sizes", sizes);

		// 발급된 쿠폰중 사용가능한 쿠폰 가져옴
		List<CouponIssuanceVO> cvoList = orderService.getCouponIssuance(email);
		log.info("cvoList >>" + cvoList);
		model.addAttribute("couponList", cvoList);

		// 발급된 쿠폰의 쿠폰 정보를 가져옴
		List<Integer> couponNums = orderService.extractCouponNums(cvoList);
		List<CouponVO> couponInfo = orderService.getCoupon(couponNums);
		log.info("couponNums >>" + couponNums);
		log.info("couponInfo >>" + couponInfo);

		// 사용가능한 쿠폰들을 매칭해 정보 추출
		List<CouponVO> matchedCouponInfoList = orderService.matchedCouponInfoList(couponNums, couponInfo, cvoList);
		log.info("matchedCouponInfoList >>>>>>>>>>>" + matchedCouponInfoList);
		model.addAttribute("matchedCouponInfoList", matchedCouponInfoList);

//		//최저주문금액을 넘은 쿠폰정보
//		List<Integer> validCouponInfoNums = orderService.extractCouponNums(couponInfo, 30000);
//		log.info("validCouponInfoNums >>" + validCouponInfoNums);
//		
//		//최저주문금액을 넘은 발급쿠폰
//		List<CouponIssuanceVO> validCoupons = orderService.filterValidCoupons(cvoList, validCouponInfoNums);
//		log.info("validCoupons >>" + validCoupons);

		// 할인율, 할인금액 중 0이 아닌 숫자 추출
		List<Integer> extractedValues = orderService.extractedValues(couponInfo);
		log.info("extractedValues >>" + extractedValues);

		// 할인 구분
		List<String> discountDivision = orderService.couponDiscountAmount(matchedCouponInfoList);
		model.addAttribute("discountDivision", discountDivision);

		// itemRewardPoints의 총합 -> 총 적립금액 산출
		int totalRewardPoints = orderService.totalRewardPoints(itemRewardPoints);
		log.info("totalRewardPoints >>" + totalRewardPoints);
		model.addAttribute("totalRewardPoints", totalRewardPoints);

		// 총 결제금액
		model.addAttribute("TotalPriceWithShipping", TotalPriceWithShipping);
	}

	// 쿠폰선택
	@PostMapping("/discountedAmount")
	@ResponseBody // 이 어노테이션을 사용하여 응답 데이터를 HTTP 응답 본문에 직접 쓸 수 있습니다.
	public Map<String, Integer> discountedAmount(int selectCouponDiscountRate, int totalPrice, int deliveryCharge,
			int point) {
		totalPrice += deliveryCharge;
		// 할인율, 할인금액 할인율 -> price * (discount/100), 할인 금액
		// 쿠폰적용 후 [할인금액, 할인된 금액+배송비]
		List<Integer> couponDiscountAmount = orderService.couponDiscountAmount(selectCouponDiscountRate, totalPrice,
				point);
		int TotalPriceWithShipping = couponDiscountAmount.get(1);
		// 응답 데이터를 Map에 담아 반환
		Map<String, Integer> response = new HashMap<>();
		response.put("couponDiscount", couponDiscountAmount.get(0));
		response.put("totalPrice", couponDiscountAmount.get(1));
		response.put("TotalPriceWithShipping", TotalPriceWithShipping);
		response.put("point", point);
		log.info("response >>>>>" + response);

		return response;
	}

}