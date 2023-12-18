package com.shard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shard.domain.ItemVO;
import com.shard.domain.SearchPageVO;
import com.shard.mapper.ItemSearchMapper;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private ItemSearchMapper mapper;
	
	@Override
	public List<ItemVO> Search(SearchPageVO SearchPageVO) {
		return mapper.getSearchAllWithPaging(SearchPageVO);
	}
	
	@Override
	public List<ItemVO> AllLatest(SearchPageVO vo) {
		return mapper.getSearchAllLatest(vo);
	}
	
	@Override
	public List<ItemVO> AllMainLatest() {
		return mapper.getAllLatest();
	}


	@Override
	public List<ItemVO> RPrice(SearchPageVO vo) {
		return mapper.getRPrice(vo);
	}

	@Override
	public List<ItemVO> HPrice(SearchPageVO vo) {
		return mapper.getHPrice(vo);
	}

	@Override
	public List<ItemVO> getCategoryHPrice(int categoryNum, SearchPageVO vo) {
		return mapper.getCategoryHPrice(categoryNum, vo);
	}
	
	@Override
	public List<ItemVO> getCategoryRPrice(int categoryNum, SearchPageVO vo) {
		return mapper.getCategoryRPrice(categoryNum, vo);
	}
	
	@Override
	public List<ItemVO> getCategoryLatest(int categoryNum, SearchPageVO vo) {
		return mapper.getCategoryLatest(categoryNum, vo);
	}
	
	@Override
	public List<ItemVO> getColorHPrice(String color, SearchPageVO vo) {
		return mapper.getColorHPrice(color, vo);
	}
	
	@Override
	public List<ItemVO> getColorRPrice(String color, SearchPageVO vo) {
		return mapper.getColorRPrice(color, vo);
	}
	
	@Override
	public List<ItemVO> getColorLatest(String color, SearchPageVO vo) {
		return mapper.getColorLatest(color, vo);
	}

	
	@Override
	public List<ItemVO> getItemHPrice(String itemName, SearchPageVO vo) {
		return mapper.getItemHPrice(itemName, vo);
	}
	
	@Override
	public List<ItemVO> getItemRPrice(String itemName, SearchPageVO vo) {
		return mapper.getItemRPrice(itemName, vo);
	}
	
	@Override
	public List<ItemVO> getItemLatest(String itemName, SearchPageVO vo) {
		return mapper.getItemLatest(itemName, vo);
	}

	
	@Override
	public List<ItemVO> getItemColorHPrice(String itemName, String color, SearchPageVO vo) {
		return mapper.getItemColorHPrice(itemName, color, vo);
	}
	
	
	@Override
	public List<ItemVO> getItemColorRPrice(String itemName, String color, SearchPageVO vo) {
		return mapper.getItemColorRPrice(itemName, color, vo);
	}
	
	@Override
	public List<ItemVO> getItemColorLatest(String itemName, String color, SearchPageVO vo) {
		return mapper.getItemColorLatest(itemName, color, vo);
	}
	
//	  @Override 
//	  public List<ItemVO> Color(String itemName) {
//		  return null; 
//	}
	 

//	@Override
//	public List<ItemVO> Category(int categoryNum) {
//		return null;
//	}

//	@Override
//	public List<ItemVO> ItemSearch(String itemName) {
//		return null;
//	}

//	@Override
//	public List<ItemVO> searchWithPaging(SearchPageVO SearchPageVO) {
//		return mapper.getItemSearchWithPaging(null, SearchPageVO);
//	}

	@Override
	public int getTotalCount() {
		return mapper.getTotalCount();
	}

	@Override
	public List<ItemVO> CategoryWithPaging(int categoryNum, SearchPageVO SearchPageVO) {
        return mapper.CategoryWithPaging(categoryNum, SearchPageVO);
	}

	@Override
	public List<ItemVO> ColorWithPaging(String color, SearchPageVO SearchPageVO) {
		return mapper.ColorWithPaging(color, SearchPageVO);
	}

	@Override
	public List<ItemVO> ItemSearchWithPaging(String itemName, SearchPageVO SearchPageVO) {
		return mapper.getItemSearchWithPaging(itemName, SearchPageVO);
	}

	@Override
	public int getItemNameCount(String itemName) {
		return mapper.getItemNameCount(itemName);
	}

	@Override
	public int getColorCount(String color) {
		return mapper.getColorCount(color);
	}

	@Override
	public int getItemColorCount(String itemName, String color) {
		return mapper.getItemColorCount(itemName, color);
	}

	@Override
	public int getCategoryCount(int categoryNum) {
		return mapper.getCategoryCount(categoryNum);
	}

	@Override
	public List<ItemVO> getItemColorWithPaging(String itemName, String color, SearchPageVO SearchPageVO) {
		return mapper.getItemColorWithPaging(itemName, color, SearchPageVO);
	}











	



}
