package com.shard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.DetailCartVO;
import com.shard.domain.ItemVO;

public interface CartMapper {

	// cart 테이블 cartnum 가져오기
	public Integer getCartnum(String email);

	// detailcart에 해당 아이템 수량 + 1
	public int detailCartCntInitUpdate(@Param("itemNum") int itemNum, @Param("cartNum") int cartNum, @Param("cnt") int cnt, @Param("size") String size);

	// cart 생성
	public void cartInsert(String email);

	// detailcart 생성
	public void detailCartInsert(@Param("itemNum") int itemNum, @Param("cartNum") int cartNum,
			@Param("list") List<Map<String, Object>> dataList); // 해당 cart - detailcart의 전체 itemNum list
	
	public void detailCartInsertSingle(@Param("itemNum") int itemNum, @Param("cartNum") int cartNum,
			@Param("cnt") int cnt, @Param("size") String size);

	public List<Integer> getItemNums(int cartNum);
	
	public List<String> getItemSizes(int cartNum);

	// 해당 cart - detailcart의 전체정보 list
	public List<DetailCartVO> getDetailCart(int cartNum);

	// itemNums의 item 정보를 가져옴
	public List<ItemVO> getItems(@Param("itemNumList") List<Integer> itemNumList);

	// 현재 고객의 membership 번호(memNum)를 가져옴
	public int getMembership(String email);

	// 현재 고객이 가진 membership의 적립률(pointRate)을 가져옴
	public int getPointRate(int memNum);

	// cart에서 선택한 itemNum이 들어간 detailcart삭제
	 public void chooseDetailCartDelete(@Param("itemNumList")List<Integer> itemNumList, @Param("cartNum")int cartNum, @Param("size")String size);

	// cartNum의 전체 detailcart 삭제
	public void allDetailCartDelete(Integer cartNum);

	// 수량수정시 detailCart에 해당 item 수량변경
	public void detailCartCntPlusUpdate(@Param("itemNum") int itemNum, @Param("size")String size);

	public void detailCartCntMinusUpdate(@Param("itemNum") int itemNum, @Param("size")String size);

}