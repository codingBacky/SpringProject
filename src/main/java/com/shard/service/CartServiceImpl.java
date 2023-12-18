package com.shard.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.shard.domain.DetailCartVO;
import com.shard.domain.ItemVO;
import com.shard.mapper.CartMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class CartServiceImpl implements CartService {
	public static final int STANDARD_DELIVERY_CHARGE = 3000;

	@Autowired
	private CartMapper mapper;

	@Override
	public void initCart(String email, int itemNum, List<Integer> cnt, List<String> size) {
		Integer cartNum = mapper.getCartnum(email);
		log.info("cartNum check >>" + cartNum);

		List<Map<String, Object>> dataList = new ArrayList<>();
		for (int i = 0; i < cnt.size(); i++) {
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("cnt", cnt.get(i));
			dataMap.put("size", size.get(i));
			dataList.add(dataMap);
		}

		MultiValueMap<Integer, String> cartList = new LinkedMultiValueMap<>();

		Boolean foundMatch = false;
		Integer userCnt = null; // 루프 밖에서 초기화
		String userSize = null; // 루프 밖에서 초기화
		List<String> optionList = new ArrayList<>();
		List<Map<String, Object>> user = new ArrayList<>();
		// cartNum으로 cart여부 체크
		// cartNum이 존재하지 않으면 cart와 해당 cart의 detailCart 생성
		if (cartNum == null) {
			mapper.cartInsert(email);
			cartNum = mapper.getCartnum(email);
			mapper.detailCartInsert(itemNum, cartNum, dataList);
			// 기존 cart가 있으면 해당상품 detail itemNum을 체크,
			// 존재하면 개수만 1 늘려주고 없으면 detailCart를 추가해준다.
		} else if (cartNum != null) {
			// cart에 item이 존재하는지 체크
			List<Integer> itemNumList = mapper.getItemNums(cartNum);
			if (itemNumList.isEmpty()) {
				mapper.detailCartInsert(itemNum, cartNum, dataList);
			} else {
				List<String> existingSizes = mapper.getItemSizes(cartNum);
				System.out.println(existingSizes);

				for (int i = 0; i < existingSizes.size(); i++) {
					cartList.add(itemNumList.get(i), existingSizes.get(i));
				}

				System.out.println(itemNumList);
				System.out.println(cartList);

				for (Map<String, Object> data : dataList) {
					for (Map.Entry<String, Object> entry : data.entrySet()) {
						Map<String, Object> map = new HashMap<>();
						map.put(entry.getKey(), entry.getValue());
						user.add(map);
					}
				}

				System.out.println(user);

				for (Entry<Integer, List<String>> cart : cartList.entrySet()) { // 4번
					int cartValue = cart.getKey();
					System.out.println("카트 벨류" + cartValue);

					for (int i = 0; i < user.size(); i += 2) { // 3번
						Map<String, Object> userMap = user.get(i);
						Map<String, Object> userMap2 = user.get(i + 1);
						userSize = (String) userMap.get("size");
						userCnt = (Integer) userMap2.get("cnt");

						if (userCnt != null) {
							if (cartValue == itemNum) {
								System.out.println("일단 이건 됨");
								optionList = cart.getValue();
								System.out.println("userSize 찍기" + userSize);
								System.out.println("userCnt 찍기" + userCnt);
								if (userSize != null) {
									System.out.println("카트에 있는 옵션" + optionList);
									boolean sizeMatch = false; // 사이즈에 대한 매치 여부 체크 변수 추가
									for (String option : optionList) {
										System.out.println(option);
										System.out.println(userSize);
										if (userSize.equals(option)) {
											System.out.println("조건이 다 맞을 때 상품이 있고 같은 사이즈가 있을 때");
											mapper.detailCartCntInitUpdate(itemNum, cartNum, userCnt, userSize);
											sizeMatch = true;
											foundMatch = false;
										} else {
											System.out.println("카트에 있는 모든 옵션을 돌면서 체크");
										}
									}
									if (!sizeMatch) {
										System.out.println("아이템 넘은 같고 사이즈 다름");
										mapper.detailCartInsertSingle(itemNum, cartNum, userCnt, userSize);
										foundMatch = false;
									}
								}
							} else {
								foundMatch = true;
								break;
							}
						}
					}
				}
			}

			if (foundMatch) {
				// 내가 추가한 각 사이즈에 대해 매치 여부를 확인
				for (int i = 0; i < user.size(); i += 2) {
					Map<String, Object> userMap = user.get(i);
					userSize = (String) userMap.get("size");
					System.out.println("여기 들어오면서 cart에 상품 추가");
					mapper.detailCartInsertSingle(itemNum, cartNum, userCnt, userSize);
				}
			}

//	              boolean itemNumExists = false;
			// itemNum=1 인 상품의 L사이즈와 XL사이즈를 장바구니에 담으려고 하는 것
			// itemNum=1 인 상품의 L사이즈와 XL사이즈를 장바구니에 담으려고 하는 것
//	              for (int i = 0; i < size.size(); i++) {
//	                  String itemSize = size.get(i);
//	                  int itemcnt = cnt.get(i);
//	                  System.out.println("for문 입성");
//	                  
//	                  // 장바구니에 이미 담겨있는 상품의 경우
//	                  if (existingSizes.contains(size)) {
//	                     System.out.println("장바구니에 들어있는 상품");
//	                      // detailCartCntInitUpdate로 업데이트 시도
//	                      int updatedRowCount = mapper.detailCartCntInitUpdate(itemNum, cartNum, itemcnt, itemSize);
//	                      // 업데이트가 실패하면 (대상 레코드가 없으면) detailCartInsert로 삽입
//	                      if (updatedRowCount == 0) {
//	                         System.out.println("updatedRowCount"+updatedRowCount);
//	                          mapper.detailCartInsertSingle(itemNum, cartNum, itemcnt, itemSize);
//	                      }
//	                  } else {
//	                      // 장바구니에 담으려는 상품이 없는 경우
//	                      // detailCartInsert로 삽입
//	                      mapper.detailCartInsertSingle(itemNum, cartNum, itemcnt, itemSize);
//	                      System.out.println("담으려는 상풍이 없는경우 새로 생");
//	                  }
//	              }
		}
	}

	@Override
	public Integer getCartnum(String email) {
		return mapper.getCartnum(email);

	}

	// 장바구니에 담긴 상품 List
	@Override
	public List<ItemVO> detailCartItems(int cartNum) {
		List<Integer> itemNumList = mapper.getItemNums(cartNum);

		// itemNumList가 비어 있다면 빈 결과를 반환
		if (itemNumList.isEmpty()) {
			return Collections.emptyList();
		}

		return mapper.getItems(itemNumList);
	}

	@Override
	public List<Integer> detailCartItemstest(int cartNum) {
		List<Integer> itemNumList = mapper.getItemNums(cartNum);
		log.info("itemNumList<><><>" + itemNumList);
		// itemNumList가 비어 있다면 빈 결과를 반환
		if (itemNumList.isEmpty()) {
			return Collections.emptyList();
		}

		return itemNumList;
	}

	// 장바구니 itemNum List
	@Override
	public List<Integer> getItemNums(int cartNum) {
		List<Integer> itemNumList = mapper.getItemNums(cartNum);
		return itemNumList;
	}

	@Override
	public int getMembership(String email) {
		return mapper.getMembership(email);
	}

	@Override
	public int getPointRate(int memNum) {
		return mapper.getPointRate(memNum);
	}

	// 배송비
	@Override
	public int deliveryCharge(int memNum) {
		int result = STANDARD_DELIVERY_CHARGE;

		// memNum이 2(memdership 등급 - shard)인 경우 배송료를 0으로 설정
		if (memNum == 2) {
			result = 0;
		}
		return result;
	}

	@Override
	public void chooseDetailCartDelete(List<Integer> itemNumList, int cartNum, String size) {
		mapper.chooseDetailCartDelete(itemNumList, cartNum, size);
	}

	@Override
	public void allDetailCartDelete(Integer cartNum) {
		mapper.allDetailCartDelete(cartNum);
	}

	// DetailOrderVO 객체에서 getCartItemCnts 메서드를 호출하여 cartItemCnts 값을 추출
	// 이를 스트림의 map 함수를 통해 각 DetailOrderVO 객체에 적용하고, 그 결과를 리스트로 수집
	@Override
	public List<Integer> extractCartItemCnts(int cartNum) {
		List<DetailCartVO> dvo = mapper.getDetailCart(cartNum);
		return dvo.stream().map(DetailCartVO::getCartItemCnt).collect(Collectors.toList());
	}

	// size 수집
	@Override
	public List<String> extractCartItemSize(int cartNum) {
		List<DetailCartVO> dvo = mapper.getDetailCart(cartNum);
		return dvo.stream().map(DetailCartVO::getSize).collect(Collectors.toList());
	}

	@Override
	public List<Integer> extractSales(List<ItemVO> ivo) {

		return ivo.stream().map(ItemVO::getSale).collect(Collectors.toList());
	}

	// 가격*수량 List
	@Override
	public List<Integer> totalPriceList(List<Integer> sales, List<Integer> itemCnts) {
		List<Integer> totalPriceList = IntStream.range(0, itemCnts.size()).mapToObj(i -> itemCnts.get(i) * sales.get(i))
				.collect(Collectors.toList());
		return totalPriceList;
	}

	// sum(가격*수량)
	@Override
	public int calculateTotalPrice(List<Integer> sales, List<Integer> itemCnts) {
		// 예외 처리: 두 리스트의 크기가 다르면 예외 처리
		if (sales.size() != itemCnts.size())
			throw new IllegalArgumentException("두 리스트의 크기가 같아야 합니다.");

		int totalPrice = 0;
		// 각 index를 곱하고 더하기
		for (int i = 0; i < sales.size(); i++) {
			int price = sales.get(i);
			int quantity = itemCnts.get(i);
			totalPrice += price * quantity;
		}
		return totalPrice;
	}

	// 상품금액에는 0.1을 곱하고 10원 단위로 내림된 값
	@Override
	public List<Integer> itemRewardPoints(List<Integer> sales, int pointRate, List<Integer> cnt) {
		List<Integer> rewardPoints = sales.stream().map(sale -> (int) Math.floor(sale * (pointRate / 100.0)))
				.collect(Collectors.toList());

		List<Integer> totalPriceList = IntStream.range(0, cnt.size()).mapToObj(i -> cnt.get(i) * rewardPoints.get(i))
				.collect(Collectors.toList());

		return totalPriceList;
	}

	@Override
	public List<ItemVO> getItems(List<Integer> itemNumList) {
		return mapper.getItems(itemNumList);
	}

	@Override
	public void cartInsert(String email) {
		mapper.cartInsert(email);
	}

	@Override
	public void detailCartCntPlusUpdate(int itemNum, String size) {
		mapper.detailCartCntPlusUpdate(itemNum, size);
	}

	@Override
	public void detailCartCntMinusUpdate(int itemNum, String size) {
		mapper.detailCartCntMinusUpdate(itemNum, size);
	}

}
