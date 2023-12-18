package com.shard.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.ItemReplyVO;
import com.shard.domain.PageVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ItemReplyMapperTest {
	
	@Autowired
	private ItemReplyMapper mapper;
	
	// 리뷰를 생성하는 테스트
	@Test
	public void InsertReplyTest() {
			ItemReplyVO vo = ItemReplyVO.builder()
					.replyTitle("이걸 이 돈주고 판다고?")
					.replyContent("당장 100000벌 사세요")
					.starScore(5)
					/* .itemRepRegDate(null) */
					.img1(null)
					.itemNum(111) //참조 무결성
					.build();
			mapper.insertReply(vo);
	}
	
	// 리뷰를 생성하는 테스트 (for문 반복)
	@Test
	public void InsertReplyRepeatTest() {
		for(int i = 1; i <=25; i++  ) {
	
			ItemReplyVO vo = ItemReplyVO.builder()
				.replyTitle("reply_TEST_title" + i)
				.replyContent("내공냠냠" + i)
				.starScore(2)
				/* .itemRepRegDate(null) */
				.img1(null)
				.itemNum(2291) //참조 무결성
				.build();
		mapper.insertReply(vo);

		}
	}
	
	
	@Test // 읽어오기
	public void readReplyTest() {
		log.info(mapper.readReply(111));
	}
	
//	@Test // num값으로 읽어오기
//	public void getReplyListTest() {
//		int itemNum = 111;
//		
//		log.info("Reply List for itemNum " + itemNum + ": " + mapper.getReplyList(itemNum));
//	}
	
	@Test // 삭제
	public void deleteReplyTest() {
		log.info(mapper.deleteReply(000));
	}
	
	@Test // 리뷰 업데이트
	public void updateReplyTest() {
		ItemReplyVO vo = ItemReplyVO.builder()
				.itemNum(2)
				.replyTitle("리뷰 1등은 나다")
				.replyContent("제곧내")
				.starScore(5)
				.itemRepRegDate(null)
				.img1(null)
				.build();
		mapper.updateReply(vo);
	}
	
//	@Test // 페이징 테스트
//	public void pagingReplyTest() {
//		PageVO vo = new PageVO(1, mapper.totalCount(2291));
//		mapper.replyList(vo);
//		log.info(vo);
//	}
}
