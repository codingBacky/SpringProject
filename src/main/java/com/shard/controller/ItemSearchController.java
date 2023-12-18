package com.shard.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shard.domain.ItemVO;
import com.shard.domain.SearchPageVO;
import com.shard.domain.SortType;
import com.shard.service.ItemSearchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@CrossOrigin
@Controller
@RequestMapping("/itemSearch/*")
@Log4j
@RequiredArgsConstructor
public class ItemSearchController {

	private final ItemSearchService service;

	@GetMapping("/list")
	public void list(@RequestParam(name = "itemName", required = false) String itemName,
			@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(name = "color", required = false) String color,
			@RequestParam(name = "sortType", defaultValue = "") String sortType, Model model) {
		System.out.println(sortType);
		if (color != "" && itemName != "") {
			if(sortType.equals("Latest")) {
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemColorCount(itemName, color));
				model.addAttribute("list", service.getItemColorLatest(itemName, color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("itemName", itemName);
				model.addAttribute("sortType", sortType);
			}else if (sortType.equals("HPrice")) {
				System.out.println("아이템 컬러 H들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemColorCount(itemName, color));
				model.addAttribute("list", service.getItemColorHPrice(itemName, color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("itemName", itemName);
				model.addAttribute("sortType", sortType);
			} else if (sortType.equals("RPrice")) {
				System.out.println("아이템 컬러 R들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemColorCount(itemName, color));
				model.addAttribute("list", service.getItemColorRPrice(itemName, color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("itemName", itemName);
				model.addAttribute("sortType", sortType);
			}else {
				System.out.println("그냥 아이템 컬러 검색");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemColorCount(itemName, color));
				model.addAttribute("list", service.getItemColorWithPaging(itemName, color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("itemName", itemName);
			}
		}

		// color가 null이 아닌 경우에만 검색 조건을 설정
		else if (color != "") {
			if(sortType.equals("Latest")) {
				System.out.println("컬러 최신순 들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getColorCount(color));
			    model.addAttribute("list", service.getColorLatest(color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("sortType", sortType);	
			}else if(sortType.equals("HPrice")) {
				System.out.println("컬러 H들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getColorCount(color));
			    model.addAttribute("list", service.getColorHPrice(color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("sortType", sortType);
			}else if (sortType.equals("RPrice")) {
				System.out.println("컬러 R들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getColorCount(color));
				model.addAttribute("list", service.getColorRPrice(color, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("color", color);
				model.addAttribute("sortType", sortType);
			}else {
			System.out.println("그냥 컬러 들어옴");
			SearchPageVO vo = new SearchPageVO(pageNum, service.getColorCount(color));
			model.addAttribute("list", service.ColorWithPaging(color, vo));
			model.addAttribute("pageMaker", vo);
			model.addAttribute("color", color);
			}
		}
		// itemName이 null이 아닌 경우에만 검색
		else if (itemName != "") {
			if(sortType.equals("Latest")) {
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemNameCount(itemName));
				model.addAttribute("list", service.getItemLatest(itemName, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("itemName", itemName);
				model.addAttribute("sortType", sortType);
			}else if(sortType.equals("HPrice")) {
				System.out.println("아이템 H들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemNameCount(itemName));
				model.addAttribute("list", service.getItemHPrice(itemName, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("itemName", itemName);
				model.addAttribute("sortType", sortType);
			}else if(sortType.equals("RPrice")) {
				System.out.println("아이템 R들어옴");
				SearchPageVO vo = new SearchPageVO(pageNum, service.getItemNameCount(itemName));
				model.addAttribute("list", service.getItemRPrice(itemName, vo));
				model.addAttribute("pageMaker", vo);
				model.addAttribute("itemName", itemName);
				model.addAttribute("sortType", sortType);
			}else {
			System.out.println("그냥 아이템 들어옴");	
			SearchPageVO vo = new SearchPageVO(pageNum, service.getItemNameCount(itemName));
			model.addAttribute("list", service.ItemSearchWithPaging(itemName, vo));
			model.addAttribute("pageMaker", vo);
			model.addAttribute("itemName", itemName);
			}
			
	}
		else {
			if(sortType.equals("Latest")) {
			System.out.println("전체 리스트 최신순 들어옴");	
	        SearchPageVO vo = new SearchPageVO(pageNum, service.getTotalCount());
	        model.addAttribute("list", service.AllLatest(vo));
	        model.addAttribute("pageMaker", vo);
	        model.addAttribute("sortType", sortType);
			}
			else if(sortType.equals("HPrice")) {
		        System.out.println("전체리스트 H들어옴");    
		        SearchPageVO vo = new SearchPageVO(pageNum, service.getTotalCount());
		        model.addAttribute("list", service.HPrice(vo));
		        model.addAttribute("pageMaker", vo);
		        model.addAttribute("sortType", sortType);
		    } else if(sortType.equals("RPrice")) {
		        System.out.println("전체리스트 R들어옴");    
		        SearchPageVO vo = new SearchPageVO(pageNum, service.getTotalCount());
		        model.addAttribute("list", service.RPrice(vo));
		        model.addAttribute("pageMaker", vo);
		        model.addAttribute("sortType", sortType);
		    } else {
		        System.out.println("그냥 전체리스트 들어옴");    
		        SearchPageVO vo = new SearchPageVO(pageNum, service.getTotalCount());
		        model.addAttribute("list", service.Search(vo));
		        model.addAttribute("pageMaker", vo);
		    }
		}

	}

	@GetMapping("/list/category/{categoryNum}")
	public String categoryList(@PathVariable(name = "categoryNum") int categoryNum,
			@RequestParam(name = "pageNum", defaultValue = "1") int pageNum, Model model) {
		SearchPageVO vo = new SearchPageVO(pageNum, service.getCategoryCount(categoryNum));
		vo.setCategoryNum(categoryNum);
		List<ItemVO> searchResult = service.CategoryWithPaging(categoryNum, vo);
		model.addAttribute("list", searchResult);
		model.addAttribute("pageMaker", vo);
		model.addAttribute("categoryNum", categoryNum);
		return "/itemSearch/categorylist";
	}

	@GetMapping("/list/category/{categoryNum}/{sortType}")
	public String priceSortedList(@PathVariable(name = "categoryNum") int categoryNum,
			@PathVariable(name = "sortType") SortType sortType,
			@RequestParam(name = "pageNum", defaultValue = "1") int pageNum, Model model) {
		if(sortType == SortType.Latest) {
			SearchPageVO vo = new SearchPageVO(pageNum, service.getCategoryCount(categoryNum));
			model.addAttribute("list", service.getCategoryLatest(categoryNum, vo));
			model.addAttribute("pageMaker", vo);
			model.addAttribute("categoryNum", categoryNum);
		}
		else if (sortType == SortType.HPrice) {
			SearchPageVO vo = new SearchPageVO(pageNum, service.getCategoryCount(categoryNum));
			model.addAttribute("list", service.getCategoryHPrice(categoryNum, vo));
			model.addAttribute("pageMaker", vo);
			model.addAttribute("categoryNum", categoryNum);
			System.out.println("categoryHP >>" + vo);
		} else if (sortType == SortType.RPrice) {
			SearchPageVO vo = new SearchPageVO(pageNum, service.getCategoryCount(categoryNum));
			model.addAttribute("list", service.getCategoryRPrice(categoryNum, vo));
			model.addAttribute("pageMaker", vo);
			model.addAttribute("categoryNum", categoryNum);
			System.out.println("itemNameSelect >>" + vo);
		} else {
			throw new IllegalArgumentException("지원하지 않는 정렬 유형: " + sortType);
		}
		return "/itemSearch/categoryPricelist";
	}
}

