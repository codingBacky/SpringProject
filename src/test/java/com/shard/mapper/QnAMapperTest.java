package com.shard.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.PageVO;
import com.shard.domain.QnAVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class QnAMapperTest {
	
	@Autowired
	private QnAMapper mapper;
	
	@Autowired
	private QnAEnswerMapper mapper2;

	@Test
	public void qnaList() {
		mapper.qnaList(new PageVO(1, mapper.totalCount())).forEach(list -> log.info(list));;
	}
	
	@Test
	public void qnaInsertTest() {
			QnAVO vo = QnAVO.builder()
						.replyTitle("문의합니다22222")
						.replyContent("문의합니다다다22222")
						.replyPwd("1234")
						.email("user1@admin.com")
						.replyCategory("배송")
						.build();
			mapper.qnaInsert(vo);
			int lastId = mapper.lastId();
			mapper.inquiryNumUpadte(lastId);
	}
	
//	@Test
//	public void listGetTest() {
//		mapper.getQnAList("user1@admin.com").forEach(list -> log.info(list));
//		
//		mapper2.enswerGetList("user1@admin.com").forEach(result -> log.info(result));
//	}
//	
//	@Test
//	public void deleteQnATest() {
//		log.info(mapper.delete("user60@user.com"));
//	}
}
