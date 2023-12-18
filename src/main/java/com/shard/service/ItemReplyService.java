package com.shard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemReplyVO;
import com.shard.domain.PageVO;

// 상품 리뷰를 위한 내용
public interface ItemReplyService {

	/* itemNum으로 가져오는 리스트 */
	public List<ItemReplyVO> getReplyList(int itemNum, PageVO vo);
	
	
	/* 리뷰 CURD */
	public int insertReply(ItemReplyVO vo, MultipartFile file); // creat 리뷰 생성 및 파일(이미지) 업로드 가능
	public int insertReply(ItemReplyVO vo);
	public ItemReplyVO readReply(int itemNum); // read 작성 리뷰 읽기
	public int updateReply(ItemReplyVO vo); // update 작성 리뷰 수정
	public int deleteReply(int replyNum); // delete replyNum으로 리뷰 삭제


	/* 리뷰 페이징 */
	public int totalCount(int itemNum);
	
	public ItemReplyVO getReply(int replyNum);

	/* replyNum으로 상품 번호 가져오기 */
	public int getItemNumByReplyNum(int replyNum);


	
	
}
