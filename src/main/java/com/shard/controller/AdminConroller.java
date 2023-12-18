package com.shard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shard.domain.ItemVO;
import com.shard.domain.NoticeVO;
import com.shard.domain.PageVO;
import com.shard.service.AdminService;
import com.shard.service.ItemService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin/*")
public class AdminConroller {

	@Autowired
	private AdminService service;

	@Autowired
	private ItemService itemService;
	

	@GetMapping("/")
	@PreAuthorize("isAuthenticated()")
	public String admin() {
		return "admin/admin";
	}

	@GetMapping("/user")
	@PreAuthorize("isAuthenticated()")
	public void adminUser(Model model, @RequestParam("pageNum") int pageNum,
			@RequestParam("userName") String userName, @ModelAttribute("deleteUser") String deleteUser) {
		if (userName != "") {
			PageVO vo = new PageVO(pageNum, service.userCount(userName));
			model.addAttribute("page", vo);
			model.addAttribute("userList", service.userSearchList(userName, vo));
			model.addAttribute("userName", userName);
			model.addAttribute("deleteUser", deleteUser);
		} else {
			PageVO vo = new PageVO(pageNum, service.userCount());
			model.addAttribute("userList", service.userList(vo));
			model.addAttribute("page", vo);
			model.addAttribute("deleteUser", deleteUser);
		}

	}

	@GetMapping("/item")
	@PreAuthorize("isAuthenticated()")
	public void adminItem(Model model, @RequestParam("pageNum") int pageNum,
			@RequestParam("itemName") String itemName, @ModelAttribute("result") String result) {
		if (itemName != "") {
			PageVO vo = new PageVO(pageNum, service.itemCount(itemName));
			model.addAttribute("itemList", service.itemSearchList(itemName, vo));
			model.addAttribute("page", vo);
			model.addAttribute("itemName", itemName);
			model.addAttribute("pageNum", pageNum);
		} else {
			PageVO vo = new PageVO(pageNum, service.itemCount());
			model.addAttribute("itemList", service.itemList(vo));
			model.addAttribute("page", vo);
			model.addAttribute("pageNum", pageNum);
		}
		model.addAttribute("result", result);
	}

	@GetMapping("/itemInsert")
	@PreAuthorize("isAuthenticated()")
	public void itemInsert() {
	}

	@PostMapping("/itemInsert")
	public String itemInsert(ItemVO vo, @RequestParam("img") List<MultipartFile> file, RedirectAttributes rttr) {
		System.out.println(vo);
		int result = itemService.itemInsert(vo, file);

		if (result == -1) { // 파일 크기가 5MB가 넘었을경우
			rttr.addFlashAttribute("file", "up");
		} else if (result == -2) { // 사진 파일이 아닐 경우
			System.out.println("사진 파일 아님");
			rttr.addFlashAttribute("file", "noImg");
		} else if (result == -3) { // 파일 업로드 중 에러가 발생한 경우
			System.out.println("사진업로드 중 에러 발생");
		}

		return "redirect:/admin/item?pageNum=1&itemName=";
	}

	@GetMapping("/itemGet")
	@PreAuthorize("isAuthenticated()")
	public String get(@RequestParam("itemNum") int itemNum, @RequestParam("pageNum") int pageNum, Model model) {
		model.addAttribute("item", itemService.getItem(itemNum));
		model.addAttribute("pageNum", pageNum);
		return "admin/get";
	}

	@GetMapping("/noEnswer")
	@PreAuthorize("isAuthenticated()")
	public void noEnswer(Model model, @RequestParam("pageNum") int pageNum) {
		PageVO vo = new PageVO(pageNum, service.noEnswerCount());
		model.addAttribute("noEnswer", service.noEnswerList(vo));
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("page", vo);
	}

	@PostMapping("/userSearch")
	public String userSearch(@RequestParam("userName") String userName, Model model,
			@RequestParam("pageNum") int pageNum) {
		PageVO vo = new PageVO(pageNum, service.userCount(userName));
		model.addAttribute("userList", service.userSearchList(userName, vo));
		model.addAttribute("page", vo);
		model.addAttribute("userName", userName);
		return "/admin/user";
	}

	@PostMapping("/itemSearch")
	public String itemSearch(@RequestParam("itemName") String itemName, Model model,
			@RequestParam("pageNum") int pageNum) {
		PageVO vo = new PageVO(pageNum, service.itemCount(itemName));
		model.addAttribute("itemList", service.itemSearchList(itemName, vo));
		model.addAttribute("page", vo);
		model.addAttribute("itemName", itemName);
		return "/admin/item";
	}

	@PostMapping("/itemUpdate")
	public String itemUpdate(ItemVO vo, @RequestParam("img") List<MultipartFile> file, RedirectAttributes rttr) {
		System.out.println(vo);
		int result = itemService.itemUpdate(vo, file);

		if (result == -1) { // 파일 크기가 5MB가 넘었을경우
			rttr.addFlashAttribute("file", "up");
		} else if (result == -2) { // 사진 파일이 아닐 경우
			System.out.println("사진 파일 아님");
			rttr.addFlashAttribute("file", "noImg");
		} else if (result == -3) { // 파일 업로드 중 에러가 발생한 경우
			System.out.println("사진업로드 중 에러 발생");
		}

		return "redirect:/admin/item?pageNum=1&itemName=";
	}
	
	@GetMapping("/deleteItem")
	@PreAuthorize("isAuthenticated()")
	public String deleteItem(ItemVO vo, RedirectAttributes rttr) {
		itemService.itemDelete(vo.getItemNum());
		rttr.addFlashAttribute("result", "deleteSuccess");
		return "redirect:/admin/item?pageNum=1&itemName=";
	}
	
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam String email, RedirectAttributes rttr) {
		service.deleteUser(email);
		rttr.addFlashAttribute("deleteUser", "deleteUser");
		return "redirect:/admin/user?pageNum=1&userName=";
	}
	
	/////////////////공지사항///////////////////////////
	
	@GetMapping("/noticeInsert")
	@PreAuthorize("isAuthenticated()")
	public void noticeInsert() {
	}
	
	@PostMapping("/noticeInsert")
	public String noticeIsnert(NoticeVO vo, @RequestParam("img") MultipartFile file, RedirectAttributes rttr) {
		if(file.getSize() == 0) {
			service.insertNotice(vo);
		}else {
			int result = service.insertNotice(vo, file);
			
			if (result == -1) { // 파일 크기가 5MB가 넘었을경우
				rttr.addFlashAttribute("file", "up");
			} else if (result == -2) { // 사진 파일이 아닐 경우
				System.out.println("사진 파일 아님");
				rttr.addFlashAttribute("file", "noImg");
			} else if (result == -3) { // 파일 업로드 중 에러가 발생한 경우
				System.out.println("사진업로드 중 에러 발생");
			}
		}
		return "redirect:/shard/notice?pageNum=1";
	}
	
	@PostMapping("/noticeDelete")
	public String noticeDelete(@RequestParam int noticeNum, RedirectAttributes rttr) {
		service.deleteNotice(noticeNum);
		rttr.addFlashAttribute("deleteNotice", "deleteNotice");
		return "redirect:/shard/notice?pageNum=1";
	}
	
	@GetMapping("/noticeUpdate")
	@PreAuthorize("isAuthenticated()")
	public void noticeUpdate(@RequestParam int noticeNum, @RequestParam int pageNum, Model model) {
		model.addAttribute("notice", service.getNotice(noticeNum));
		model.addAttribute("pageNum", pageNum);
	}
	
	@PostMapping("/noticeUpdate")
	public String noticeUpdate(NoticeVO vo, @RequestParam("img") MultipartFile file, RedirectAttributes rttr) {
		if(file.getSize() == 0) {
			NoticeVO notice = service.getNotice(vo.getNoticeNum());
			vo.setNoticeImg(notice.getNoticeImg());
			service.updateNotice(vo);
		}else {
			int result = service.updateNotice(vo, file);
			
			if (result == -1) { // 파일 크기가 5MB가 넘었을경우
				rttr.addFlashAttribute("file", "up");
			} else if (result == -2) { // 사진 파일이 아닐 경우
				System.out.println("사진 파일 아님");
				rttr.addFlashAttribute("file", "noImg");
			} else if (result == -3) { // 파일 업로드 중 에러가 발생한 경우
				System.out.println("사진업로드 중 에러 발생");
			}
		}
		
		return "redirect:/shard/notice?pageNum=1";
	}
	
	////////////통계/////////////////////
	@GetMapping("/statistics")
	@PreAuthorize("isAuthenticated()")
	public void adminUser(Model model, ItemVO vo) {
		int t1 = service.statisticsCount(156);
		int t2 = service.statisticsCount(100);
		int t3 = service.statisticsCount(64);
		int t4 = service.statisticsCount(4);
		 // 통계 카운트용 
		model.addAttribute("t1", t1);
		model.addAttribute("t2", t2);
		model.addAttribute("t3", t3);
		model.addAttribute("t4", t4);
	}
}
