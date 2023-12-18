package com.shard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemVO;
import com.shard.domain.NoticeVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.PageVO;
import com.shard.domain.QnAVO;
import com.shard.domain.ShardMemberVO;

public interface AdminService {
	public List<ShardMemberVO> userList(PageVO vo);

	public List<ItemVO> itemList(PageVO vo);

	public List<QnAVO> noEnswerList(PageVO vo);

	public int userCount();

	public int itemCount();

	public int noEnswerCount();
	
	public void deleteUser(String email);

	public List<ShardMemberVO> userSearchList(String name, PageVO vo);

	public List<ItemVO> itemSearchList(String itemName, PageVO vo);

	public int userCount(String name);

	public int itemCount(String itemName);


	public NoticeVO getNotice(int noticeNum);

	public int insertNotice(NoticeVO vo, MultipartFile file);
	
	public void insertNotice(NoticeVO vo);

	public void deleteNotice(int noticeNum);
	
	public int updateNotice(NoticeVO vo, MultipartFile file);

	public void updateNotice(NoticeVO vo);

	public List<NoticeVO> noticeList(PageVO vo);

	public int noticeCount();
	
	public int statisticsCount(int orderId);
	


}