package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.ItemVO;

public interface ItemMapper {

// insert, update 의 기능은 관리자에서 구현한다고 영수가 말해줌!

//	단건 데이터 조회
	public ItemVO getItem(int itemNum);

	public int itemInsert(ItemVO vo);

	public void itemDelete(int itemNum);

	public int itemUpdate(ItemVO vo);

	// 상품문의를 위한, 상품 번호를 바탕으로 이름 가져오기
	public String getItemNameByItemNum(int itemNum);

	public int wishListSelect(@Param("itemNum") int itemNum, @Param("email") String email);

	public void wishListInsert(@Param("itemNum") int itemNum, @Param("email") String email);

	public void wishListDelete(@Param("itemNum") int itemNum, @Param("email") String email);
	
	public List<Integer> getWishListItemNum(String email);
}
