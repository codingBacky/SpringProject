package com.shard.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.ItemVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.ShardMemberVO;
import com.shard.mapper.OrdersMapper;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableTransactionManagement
@Service
@Log4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrdersMapper mapper;

	@Override
	public ShardMemberVO getCustomer(String email) {
		return mapper.getCustomer(email);
	}

	@Override
	public List<Integer> getDetailOrder(int orderId) {
		return mapper.getDetailOrder(orderId);
	}

	@Override
	public List<ItemVO> getDetailOrderItems(List<Integer> itemNumList) {
		return mapper.getDetailOrderItems(itemNumList);
	}

	@Override
	public ItemVO getItem(int itemNum) {
		return mapper.getItem(itemNum);
	}

	// 기본배송지 가져오기
	@Override
	public DeliverAddrVO getDefaultAddress(String email) {
		return mapper.getDefaultAddress(email);
	}

	// 발급쿠폰
	@Override
	public List<CouponIssuanceVO> getCouponIssuance(String email) {
		return mapper.getCouponIssuance(email);
	}

	// CouponIssuanceVO list 에서 couponNum추출
	@Override
	public List<Integer> extractCouponNums(List<CouponIssuanceVO> cvo) {
		return cvo.stream().map(CouponIssuanceVO::getCouponNum).collect(Collectors.toList());
	}

	// 쿠폰당 할인(할인율, 금액할인) 가져오기
	@Override
	public List<CouponVO> getCoupon(List<Integer> couponNumList) {
		return mapper.getCoupon(couponNumList);
	}

	// 쿠폰 할인율, 할인금액 추출
	@Override
	public List<Integer> extractedValues(List<CouponVO> couponInfo) {
		// 할인율, 할인금액 중 0이 아닌 숫자 추출
		List<Integer> extractedValues = couponInfo.stream()
				.filter(coupon -> coupon.getDiscountRate() != 0 || coupon.getDiscountAmount() != 0)
				.map(coupon -> coupon.getDiscountRate() != 0 ? coupon.getDiscountRate() : coupon.getDiscountAmount())
				.collect(Collectors.toList());
		return extractedValues;

	}

	// 쿠폰할인 적용
	@Override
	public List<Integer> couponDiscountAmount(int selectCouponDiscountRate, int totalPrice, int point) {
		int couponDiscount = 0; // 빼준 값 저장 변수

		// 추출된 값이 100보다 크면 전체금액에서 그 값을 빼주고, 100보다 작으면 값에 /100을 해주고 전체금액에서 그 값을 곱한 금액을
		// 전체금액에서 빼주기
		if (selectCouponDiscountRate > 100) {
			// 100보다 큰 경우: 전체금액에서 값 빼기
			couponDiscount += selectCouponDiscountRate;
			totalPrice -= point;
			totalPrice -= selectCouponDiscountRate;

		} else {
			// 100보다 작은 경우: 값에 /100을 하고 전체금액에서 그 값을 곱하여 뺄셈
			double discountRate = selectCouponDiscountRate / 100.0;
			// int discount = (int) ((totalPrice-point) * discountRate);
			// 최소 0으로 제한
			int discount = (int) Math.min((totalPrice - point), (totalPrice - point) * discountRate);
			couponDiscount += discount;
			totalPrice -= point;
			totalPrice -= discount;
		}

		// totalDiscount와 totalPrice를 포함한 리스트를 반환
		return Arrays.asList(couponDiscount, totalPrice);
	}

	// 쿠폰 구분
	@Override
	public List<String> couponDiscountAmount(List<CouponVO> extractedValues) {
		return extractedValues.stream().map(coupon -> coupon.getDiscountRate() > 100 ? "원 할인" : "% 할인")
				.collect(Collectors.toList());
	}

	// 사용가능한 쿠폰 리스트의 쿠폰 정보
	@Override
	public List<CouponVO> matchedCouponInfoList(List<Integer> couponNums, List<CouponVO> couponInfo,
			List<CouponIssuanceVO> cvoList) {
		List<CouponVO> matchedCouponInfoList = new ArrayList<>();
		for (int couponNum : couponNums) {
			// CouponIssuanceVO 리스트에서 couponNum과 일치하는 쿠폰을 찾음
			Optional<CouponIssuanceVO> matchedIssuance = cvoList.stream()
					.filter(issuance -> issuance.getCouponNum() == couponNum).findFirst();

			// couponNum에 해당하는 발급된 쿠폰을 찾았을 경우에만 매칭된 CouponVO를 찾아 리스트에 추가
			matchedIssuance.ifPresent(issuance -> {
				Optional<CouponVO> matchedCoupon = couponInfo.stream()
						.filter(coupon -> coupon.getCouponNum() == issuance.getCouponNum()).findFirst();

				matchedCoupon.ifPresent(matchedCouponInfoList::add);
			});
		}
		return matchedCouponInfoList;
	}

	// 배송지 수정할때 기본배송지로 수정할 경우 기존 기본배송지 ->defaultWhether =0 으로 변경
	// 배송지 수정할때 기본배송지에서 일반배송지로는 수정불가 다른 배송지를 기본배송지로 정할경우에 일반배송지로 변경 후 배송지 수정
	@Override
	public void deliverAddrUpdate(DeliverAddrVO dvo, int addrNum, String email) {
		DeliverAddrVO defaultAddress = mapper.getDefaultAddress(email);
		int defaultAddressNum = defaultAddress.getAddrNum();
		mapper.defaultAddressUpdate(defaultAddressNum);
		mapper.deliverAddrUpdate(dvo, addrNum);
	}

	// 배송지를 추가할때 기본배송지를 새로 추가할 경우 기존 기본배송지 ->defaultWhether =0 으로 변경 후 새배송지 추가
	@Override
	public void addDeliverAddr(DeliverAddrVO dvo, String email) {
//		DeliverAddrVO defaultAddress= mapper.getDefaultAddress(email);
//		int defaultAddressNum = defaultAddress.getAddrNum();
//		mapper.defaultAddressUpdate(defaultAddressNum);
		mapper.addDeliverAddr(dvo);
	}

	// 결제완료(주문완료 후)
	@Override
	public int orderComplete(OrdersVO ovo, List<Integer> itemNum, String email) {
		mapper.orderInsert(ovo);
		log.info("오더완");
		int result = mapper.getLastInsertId();
		log.info(result);

		return result;
	}

	@Override
	public void orderComplete2(DetailOrderVO dovo, int orderId) {
		mapper.detailOrderInsert(dovo, orderId);
	}

	@Override
	public void orderComplete3(int orderId, int issueNum, int usePoint, String email) {
		// 쿠폰에 orderId추가(사용한 쿠폰)
		mapper.useCouponIssuanceUpdate(orderId, issueNum);
		// 고객 포인트 차감
		mapper.customerPointUpdate(usePoint, email);
	}

	@Override
	public void onlyOrderComplete(int cartNum, List<Integer> itemNum) {
		// 주문한 아이템 장바구니에서 삭제
		mapper.cartDelete(cartNum, itemNum);
	}

	// 주문취소
	@Transactional
	@Override
	public void orderCancle(int orderId) {
		int point = mapper.getUsePoint(orderId);
		mapper.couponReturnUpdate(orderId);
		mapper.payCompleteReturnUpdate(orderId);
		mapper.customerPointReturnUpdate(point);
	}

	// DetailOrderVO 객체에서 getCartItemCnts 메서드를 호출하여 cartItemCnts 값을 추출
	// 이를 스트림의 map 함수를 통해 각 DetailOrderVO 객체에 적용하고, 그 결과를 리스트로 수집
	@Override
	public List<Integer> extractCartItemCnts(List<DetailOrderVO> dvo) {
		return dvo.stream().map(DetailOrderVO::getCnt).collect(Collectors.toList());
	}

	@Override
	public int calculateTotalAmount(int sale, List<Integer> cnts) {
		int totalDiscount = 0;

		for (int cnt : cnts) {
			int discount = sale * cnt; // 상품 가격에 상품 갯수를 곱한 값이 할인 금액
			totalDiscount += discount; // 각 상품의 할인을 더함
		}

		return totalDiscount;
	}

	// ItemVO를 List형식으로 변환
	@Override
	public List<ItemVO> flattenItemList(ItemVO itemVO) {
		List<ItemVO> flattenedList = new ArrayList<>();
		if (itemVO != null) {
			flattenedList.add(itemVO);
			List<ItemVO> subItemList = itemVO.getItemList();
			if (subItemList != null && !subItemList.isEmpty()) {
				for (ItemVO subItem : subItemList) {
					flattenedList.addAll(flattenItemList(subItem));
				}
			}
		}
		return flattenedList;
	}

	// order item 전체 적립될 포인트
	@Override
	public int totalRewardPoints(List<Integer> itemRewardPoints) {
		// List<Integer>의 값을 더하는 메소드
		return itemRewardPoints.stream().mapToInt(Integer::intValue).sum();
	}
//	
//	//최저주문금액을 넘은 쿠폰정보
//	@OverrideS
//	public List<Integer> extractCouponNums(List<CouponVO> couponList, int totalPrice) {
//		  List<Integer> validCouponNums = new ArrayList<>();
//
//		    for (CouponVO coupon : couponList) {
//		        if (coupon.getMinAmount() <= totalPrice) {
//		            validCouponNums.add(coupon.getCouponNum());
//		        }
//		    }
//		    return validCouponNums;
//	}
//	//최저주문금액을 넘은 발급쿠폰
//	@Override
//	public List<CouponIssuanceVO> filterValidCoupons(List<CouponIssuanceVO> cvoList, List<Integer> validCouponNums) {
//		List<CouponIssuanceVO> validCoupons = new ArrayList<>();
//
//	    for (CouponIssuanceVO issuedCoupon : cvoList) {
//	        if (validCouponNums.contains(issuedCoupon.getCouponNum())) {
//	            validCoupons.add(issuedCoupon);
//	        }
//	    }
//	    return validCoupons;
//	}
//	
//	

	@Override
	public int cartItemCount(String email) {
		return mapper.cartItemCount(email);
	}

	@Override
	public List<DeliverAddrVO> getUserAddress(String email) {
		return mapper.getUserAddress(email);
	}

	@Override
	public int couponCount(String email) {
		return mapper.couponCount(email);
	}

	@Override
	public List<CouponVO> couponList() {
		return mapper.couponList();
	}
	//배송완료 후 
	//세달동안 주문한 금액산출
	@Override
	public int totalAmountForLast3Months(String email) {
		int totalAmountForLast3Months = mapper.getTotalAmountForLast3Months(email);
		return totalAmountForLast3Months;
	}
	//멤버십 업데이트
	@Override
	public void membershipUpdate(int memNum, String email) {
		mapper.membershipUpdate(memNum, email);
	}
	//멤버십 업데이트
	@Override
	public void customerPointPlus(int totalRewardPoints, String email) {
		mapper.customerPointPlus(totalRewardPoints, email);
	}

	
	///////////////////////////////////////////////////////////////////
	@Override
	public List<OrdersVO> userOrders(String email) {
		return mapper.userOrders(email);
	}

	@Override
	public void deliverComplete(int orderId) {
		mapper.deliverComplete(orderId);
	}

	@Override
	public List<Integer> orderIdList(String email) {
		return mapper.orderIdList(email);
	}

	@Override
	public List<Integer> orderItemNumList(int orderId) {
		return mapper.orderItemNumList(orderId);
	}

}
