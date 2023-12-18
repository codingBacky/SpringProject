package com.shard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.ItemReplyVO;
import com.shard.domain.PageVO;

public interface ItemReplyMapper {
	
	/* itemNum으로 가져오는 리스트 */
	public List<ItemReplyVO> getReplyList(@Param("itemNum") int itemNum, @Param("vo") PageVO vo); //2개 이상을 담아오기 때문에 param으로 각각 이름을 지정해줘서 가져와야 한다.
	
	
	/* 리뷰 CURD */
	public int insertReply(ItemReplyVO vo, MultipartFile file); // creat 리뷰 생성 및 파일(이미지) 업로드 가능
	public int insertReply(ItemReplyVO vo);
	public ItemReplyVO readReply(int replyNum); // Read
	public int updateReply(ItemReplyVO vo); // 리뷰를 업데이트 할 때
	public int deleteReply(int replyNum); //리뷰를 삭제 할 때, primary key인 이메일 값을 대조해서 삭제를 하려고 했으나 테스트 중 발생한 문제로 num값
	
	/* 리뷰 페이징 */
	public int totalCount(int itemNum);
	public ItemReplyVO getReply(int replyNum);
	
	// replyNum으로 상품 번호 가져오기
	public int getItemNumByReplyNum(int replyNum);
}
