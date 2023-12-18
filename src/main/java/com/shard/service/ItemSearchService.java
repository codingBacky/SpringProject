package com.shard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.ItemVO;
import com.shard.domain.SearchPageVO;

public interface ItemSearchService {

	public List<ItemVO> Search(SearchPageVO SearchPageVO);
	public List<ItemVO> AllLatest(SearchPageVO vo);
	public List<ItemVO> AllMainLatest(); //메인페이지용
	public List<ItemVO> RPrice(SearchPageVO vo);
	public List<ItemVO> HPrice(SearchPageVO vo);
	public List<ItemVO> getCategoryHPrice(int categoryNum, SearchPageVO vo);
	public List<ItemVO> getCategoryRPrice(int categoryNum, SearchPageVO vo);
	public List<ItemVO> getCategoryLatest(int categoryNum, SearchPageVO vo);	
	public List<ItemVO> getColorHPrice(String color, SearchPageVO vo);
	public List<ItemVO> getColorRPrice(String color, SearchPageVO vo);
	public List<ItemVO> getColorLatest(String color, SearchPageVO vo);
	public List<ItemVO> getItemHPrice(String itemName, SearchPageVO vo);
	public List<ItemVO> getItemRPrice(String itemName, SearchPageVO vo);
	public List<ItemVO> getItemLatest(String itemName, SearchPageVO vo);
	public List<ItemVO> getItemColorHPrice(String itemName, String color, SearchPageVO vo);
	public List<ItemVO> getItemColorRPrice(String itemName, String color, SearchPageVO vo);
	public List<ItemVO> getItemColorLatest(String itemName, String color, SearchPageVO vo);
	//	public List<ItemVO> Color(@Param("itemName") String itemName);
	public List<ItemVO> ColorWithPaging(String color, SearchPageVO SearchPageVO);
//	public List<ItemVO> Category(@Param("itemName") int categoryNum);
	public List<ItemVO> CategoryWithPaging(int categoryNum, SearchPageVO SearchPageVO);
//	public List<ItemVO> ItemSearch(@Param("itemName") String itemName);
	public List<ItemVO> ItemSearchWithPaging(String itemName, SearchPageVO SearchPageVO);
//	public List<ItemVO> searchWithPaging(SearchPageVO SearchPageVO);
	public List<ItemVO> getItemColorWithPaging(String itemName, String color, SearchPageVO SearchPageVO);
	public int getItemNameCount(String itemName);
	public int getColorCount(String color);
	public int getItemColorCount(String itemName, @Param("color")String color);
	public int getCategoryCount(int categoryNum);
	public int getTotalCount();
}
