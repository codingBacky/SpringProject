package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shard.domain.ItemVO;
import com.shard.domain.NoticeVO;
import com.shard.domain.OrdersVO;
import com.shard.domain.PageVO;
import com.shard.domain.QnAVO;
import com.shard.domain.ShardMemberVO;

public interface AdminMapper {
	//////////////////관리자 메인 페이지 /////////////////////////
	public List<ShardMemberVO> userList(PageVO vo);

	public List<ItemVO> itemList(PageVO vo);
	
	public List<QnAVO> noEnswerList(PageVO vo);

	public int userCount();

	public int itemCount();
	
	public int noEnswerCount();
	
	///////////////////회원 삭제//////////////////////////////////
	public void deleteUser(String email);
	
	
	////////////////관리자 회원 및 상품 검색///////////////////////////
	
	public List<ShardMemberVO> userSearchList(@Param("userName") String userName, @Param("vo") PageVO vo);
	
	public List<ItemVO> itemSearchList(@Param("itemName")String itemName, @Param("vo")PageVO vo);
	
	public int userSearchCount(@Param("userName") String userName);

	public int itemSearchCount(@Param("itemName")String itemName);
	
	// 공지사항 CRUD
	
	public NoticeVO getNotice(int noticeNum);
	
	public void insertNotice(NoticeVO vo);
	
	public void deleteNotice(int noticeNum);
	
	public void updateNotice(NoticeVO vo);
	
	public List<NoticeVO> noticeList(PageVO vo);
	
	public int noticeCount();
	
	public int statisticsCount(@Param("orderId")int orderId);
}
