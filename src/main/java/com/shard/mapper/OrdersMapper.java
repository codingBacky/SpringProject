package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;

import com.shard.domain.CouponIssuanceVO;
import com.shard.domain.CouponVO;
import com.shard.domain.DeliverAddrVO;
import com.shard.domain.DetailOrderVO;
import com.shard.domain.ItemVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.ShardMemberVO;

public interface OrdersMapper {
	// 주문확인 후 pay로 넘어갈때 orders 생성
	public void orderInsert(@Param("ovo") OrdersVO ovo);

	// 삽입 후 생성된 키 받기
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "orderId", before = false, resultType = int.class)
	public int getLastInsertId();

	// detailOrder 생성
	public void detailOrderInsert(@Param("dvo") DetailOrderVO dvo, @Param("orderId") int orderId);

	// 주문 데이터 가져오기
	public ShardMemberVO getCustomer(String email);

	public List<Integer> getDetailOrder(int orderId);

	public List<ItemVO> getDetailOrderItems(@Param("itemNumList") List<Integer> itemNumList);

	public List<CouponIssuanceVO> getCouponIssuance(String email);

	public int couponCount(String email);

	public List<CouponVO> couponList(); // 쿠폰의 모든 정보

	public int cartItemCount(String email);

	public List<CouponVO> getCoupon(@Param("couponNumList") List<Integer> couponNumList);

	public ItemVO getItem(int itemNum);

	// 고객 기본배송지 가져오기
	public DeliverAddrVO getDefaultAddress(String email);

	// 고객의 모든 배송지 가져오기
	public List<DeliverAddrVO> getUserAddress(String email);

	// 고객 기본배송지 -> 일반배송지로 수정
	public void defaultAddressUpdate(int addrNum);

	// 배송지 저장(추가)
	public void addDeliverAddr(@Param("dvo") DeliverAddrVO dvo);

	// 배송지 수정
	public void deliverAddrUpdate(@Param("dvo") DeliverAddrVO dvo, @Param("addrNum") int addrNum);

//	//주문 페이지를 벗어날 경우 주문 삭제
//	//참조된 detailOrder먼저 삭제 후 orders삭제
//	public void detailOrdersDelete(int orderId);
//	public void ordersDelete(int orderId);
	// 결제완료(주문완료)
	public void cartDelete(@Param("cartNum") int cartNum, @Param("itemNumList") List<Integer> itemNumList);

	public void useCouponIssuanceUpdate(@Param("orderId") int orderId, @Param("issueNum") int issueNum);

	public void customerPointUpdate(@Param("usePoint") int usePoint, @Param("email") String email);

	public void ordersUpdate(@Param("usePoint") int usePoint, @Param("orderId") int orderId);

	// 주문 취소시
	// 주문검색 usePoint select
	public int getUsePoint(int orderId);

	// 사용했던 쿠폰 return
	public void couponReturnUpdate(int orderId);

	// payComplete->0로 변경
	public void payCompleteReturnUpdate(int orderId);

	// 사용했던 point retune
	public void customerPointReturnUpdate(int usePoint);

	// 배송완료 후 membershipUpdate
	//배송완료 후 membershipUpdate
		public int getTotalAmountForLast3Months(String email);
		public int membershipUpdate(@Param("memNum") int memNum, @Param("email") String email);
		public void customerPointPlus(@Param("totalRewardPoints") int totalRewardPoints, @Param("email") String email);
		
	
	
	
	
	
	
	///////////////마이페이지 주문 관리////////////////
	public List<OrdersVO> userOrders(String email);
	
	public void deliverComplete(int orderId);
	
	public List<Integer> orderIdList(String email);
	
	public List<Integer> orderItemNumList(int orderId);
	
	
}