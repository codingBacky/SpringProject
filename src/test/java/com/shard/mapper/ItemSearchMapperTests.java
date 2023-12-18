package com.shard.mapper;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.ItemVO;
import com.shard.domain.SearchPageVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ItemSearchMapperTests {

	@Autowired
	private ItemSearchMapper mapper;
	
	@Test
	public void testGetSearchAll(){
		SearchPageVO vo = new SearchPageVO(1,mapper.getTotalCount());
		mapper.getSearchAll(vo).forEach(list -> log.info(list));
	}
	
	@Test
	public void testGetSearchAllLatest(){
		SearchPageVO vo = new SearchPageVO(1,mapper.getTotalCount());
		mapper.getSearchAllLatest(vo).forEach(list -> log.info(list));
	}

	@Test
	public void testGetHPrice(){
		SearchPageVO vo = new SearchPageVO(1,mapper.getTotalCount());
		mapper.getHPrice(vo).forEach(list -> log.info(list));
	}
	@Test
	public void testGetRPrice(){
		SearchPageVO vo = new SearchPageVO(1,mapper.getTotalCount());
		mapper.getRPrice(vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetCategoryHPrice(){
		int categoryNum = 2;
		SearchPageVO vo = new SearchPageVO(1,mapper.getCategoryCount(categoryNum));
		mapper.getCategoryHPrice(categoryNum, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetCategoryRPrice(){
		int categoryNum = 2;
		SearchPageVO vo = new SearchPageVO(1,mapper.getCategoryCount(categoryNum));
		mapper.getCategoryRPrice(categoryNum, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetCategoryLatest(){
		int categoryNum = 2;
		SearchPageVO vo = new SearchPageVO(1,mapper.getCategoryCount(categoryNum));
		mapper.getCategoryLatest(categoryNum, vo).forEach(list -> log.info(list));
	}
	
	
	@Test
	public void testGetColorHPrice(){
		String color = "블랙";
		SearchPageVO vo = new SearchPageVO(1,mapper.getColorCount(color));
		mapper.getColorHPrice(color, vo).forEach(list -> log.info(list));	
	}
	@Test
	public void testGetColorRPrice(){
		String color = "블랙";
		SearchPageVO vo = new SearchPageVO(1,mapper.getColorCount(color));
		mapper.getColorRPrice(color, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetColorLatest(){
		String color = "블랙";
		SearchPageVO vo = new SearchPageVO(1,mapper.getColorCount(color));
		mapper.getColorLatest(color, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetItemHPrice(){
		String itemName = "맨투맨";
		SearchPageVO vo = new SearchPageVO(1,mapper.getItemNameCount(itemName));
		mapper.getItemHPrice(itemName, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetItemRPrice(){
		String itemName = "맨투맨";
		SearchPageVO vo = new SearchPageVO(1,mapper.getItemNameCount(itemName));
		mapper.getItemRPrice(itemName, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetItemLatest(){
		String itemName = "맨투맨";
		SearchPageVO vo = new SearchPageVO(1,mapper.getItemNameCount(itemName));
		mapper.getItemLatest(itemName, vo).forEach(list -> log.info(list));
	}
	
	@Test
	public void testGetItemColorHPrice(){
		String itemName = "맨투맨";
		String color = "블랙";
		SearchPageVO vo = new SearchPageVO(1,mapper.getItemColorCount(itemName, color));
		mapper.getItemColorHPrice(itemName, color, vo).forEach(list -> log.info(list));	
	}
	
	
	@Test
	public void testGetItemColorRPrice(){
		String itemName = "맨투맨";
		String color = "블랙";
		SearchPageVO vo = new SearchPageVO(1,mapper.getItemColorCount(itemName, color));
		mapper.getItemColorRPrice(itemName, color, vo).forEach(list -> log.info(list));	
	}
	
	@Test
	public void testGetItemColorLastest(){
		String itemName = "맨투맨";
		String color = "블랙";
		SearchPageVO vo = new SearchPageVO(1,mapper.getItemColorCount(itemName, color));
		mapper.getItemColorLatest(itemName, color, vo).forEach(list -> log.info(list));	
	}
	
	
	
	/*
	 * @Test public void testGetColor(){
	 * mapper.getColor("베이지").forEach(list->log.info(list)); }
	 */
	
  	@Test
    public void testGetColorWithPaging() {
        String color = "레드"; 
        SearchPageVO vo = new SearchPageVO(1, mapper.getColorCount(color));
        mapper.ColorWithPaging(color, vo).forEach(list -> log.info(list));
    }
	
	 
		/*
		 * @Test public void testGetCategory(){ mapper.getCategory(1).forEach(list ->
		 * log.info(list)); }
		 */
	  
  	@Test
    public void testGetCategoryWithPaging() {
        int categoryNum = 2; 
        SearchPageVO vo = new SearchPageVO(1, mapper.getCategoryCount(categoryNum));
		/*
		 * List<ItemVO> itemList = mapper.getCategory(SearchPageVO); itemList.forEach(item ->
		 * log.info(item));
		 */
        mapper.CategoryWithPaging(categoryNum, vo).forEach(list -> log.info(list));
    }
	
	/*
	 * @Test public void testGetItemSearch(){
	 * mapper.getItemSearch("오버핏").forEach(list -> log.info(list)); }
	 */
  	
  	@Test
    public void testGetItemSearchWithPaging() {
        String itemName = "후드"; 
        SearchPageVO vo = new SearchPageVO(1, mapper.getItemNameCount(itemName));
        mapper.getItemSearchWithPaging(itemName, vo).forEach(list -> log.info(list));
    }
  	
  
	  @Test 
	  public void testGetSearchAllWithPaging(){
		  SearchPageVO vo = new SearchPageVO(1,mapper.getTotalCount());
		  mapper.getSearchAllWithPaging(vo).forEach(list -> log.info(list)); 
	  }
	  
	  @Test 
	  public void testGetgetItemColorWithPaging(){
		  String itemName = "후드";
		  String color = "블랙";
		  SearchPageVO vo = new SearchPageVO(1,mapper.getItemColorCount(itemName, color));
		  mapper.getItemColorWithPaging(itemName, color, vo).forEach(list -> log.info(list)); 
	  }
	 
	  @Test 
	  public void testGetTotalCount(){
		  mapper.getTotalCount(); 
	  }
	  
	  @Test 
	  public void testGetItemNameCount(){
		  String itemName = "맨투맨";  
		  mapper.getItemNameCount(itemName); 
	  }
	  
	  @Test 
	  public void testGetColorCount(){
		  String color = "블랙";  
		  mapper.getColorCount(color); 
	  }
	  
	  @Test 
	  public void testGetItemColorCount(){
	      String itemName = "맨투맨";
	      String color = "블랙";
	      int count = mapper.getItemColorCount(itemName, color);
	  }
	  
	  @Test 
	  public void testGetCategoryCount(){
	      int categoryNum=2;
	      int count = mapper.getCategoryCount(categoryNum);
	  }

	
  
}
