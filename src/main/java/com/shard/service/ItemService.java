package com.shard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemVO;

public interface ItemService {
	public ItemVO getItem(int itemNum);

	public int itemInsert(ItemVO vo, List<MultipartFile> file);

	public void itemDelete(int itemNum);

	public int itemUpdate(ItemVO vo, List<MultipartFile> file);

	// 상품문의를 위한, 상품 번호를 바탕으로 이름 가져오기
	public String getItemNameByItemNum(int itemNum);

	public int wishListSelect(int itemNum, String email);

	public void wishListInsert(int itemNum, String email);

	public void wishListDelete(int itemNum, String email);
	
	public List<Integer> getWishListItemNum(String email);
}
