package com.shard.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shard.domain.QnAEnswerVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class QnAEnswerMapperTest {
	
	@Autowired
	private QnAEnswerMapper mapper;

//	@Test
//	public void InsertEnswerTest() {
//		mapper.enswerGetList("user1@admin.com").forEach(list -> log.info(list));
//	}
	
	@Test
	public void insertAndupdateEnswer() {
		QnAEnswerVO vo = QnAEnswerVO.builder()
				.enswerContent("고객님의 문의에 답변을 달았습니다.")
				.replyNum(13)
				.email("user1@admin.com")
				.build();
		mapper.insertEnswer(vo);
		
		mapper.updateComplete(13);
	}

}
