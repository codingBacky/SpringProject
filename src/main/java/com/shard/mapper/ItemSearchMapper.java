package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.ItemVO;
import com.shard.domain.SearchPageVO;

public interface ItemSearchMapper {

	public List<ItemVO> getSearchAll(SearchPageVO vo);
	public List<ItemVO> getSearchAllLatest(SearchPageVO vo);
	public List<ItemVO> getAllLatest(); //메인페이지용
	public List<ItemVO> getRPrice(SearchPageVO vo);
	public List<ItemVO> getHPrice(SearchPageVO vo);
	public List<ItemVO> getCategoryHPrice(@Param("categoryNum") int categoryNum, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getCategoryRPrice(@Param("categoryNum") int categoryNum, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getCategoryLatest(@Param("categoryNum") int categoryNum, @Param("vo") SearchPageVO vo);	
	public List<ItemVO> getColorHPrice(@Param("color")String color, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getColorRPrice(@Param("color")String color, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getColorLatest(@Param("color")String color, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getItemHPrice(@Param("itemName") String itemName, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getItemRPrice(@Param("itemName") String itemName, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getItemLatest(@Param("itemName") String itemName, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getItemColorHPrice(@Param("itemName") String itemName, @Param("color")String color, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getItemColorRPrice(@Param("itemName") String itemName, @Param("color")String color, @Param("vo") SearchPageVO vo);
	public List<ItemVO> getItemColorLatest(@Param("itemName") String itemName, @Param("color")String color, @Param("vo") SearchPageVO vo);
	//	public List<ItemVO> getColor(SearchPageVO SearchPageVO);
	public List<ItemVO> ColorWithPaging(@Param("color")String color,@Param("vo") SearchPageVO vo);
//	public List<ItemVO> getCategory(SearchPageVO SearchPageVO);
	public List<ItemVO> CategoryWithPaging(@Param("categoryNum") int categoryNum, @Param("vo")SearchPageVO SearchPageVO);
//	public List<ItemVO> getItemSearch(SearchPageVO SearchPageVO);
	public List<ItemVO> getItemSearchWithPaging(@Param("itemName") String itemName, @Param("vo")SearchPageVO SearchPageVO);	
	public List<ItemVO> getItemColorWithPaging(@Param("itemName") String itemName, @Param("color")String color, @Param("vo")SearchPageVO SearchPageVO);
	public List<ItemVO> getSearchAllWithPaging(SearchPageVO SearchPageVO);
	public int getItemNameCount(@Param("itemName")String itemName);
	public int getColorCount(@Param("color")String color);
	public int getItemColorCount(@Param("itemName") String itemName, @Param("color")String color);
	public int getCategoryCount(@Param("categoryNum") int categoryNum);
	public int getTotalCount();	

}
