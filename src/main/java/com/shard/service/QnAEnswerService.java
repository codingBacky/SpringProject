package com.shard.service;

import java.util.List;

import com.shard.domain.QnAEnswerVO;

public interface QnAEnswerService {
	public int insertEnswer(QnAEnswerVO vo); // 답변 달기
	
	public int updateComplete(int replyNum); // 답변을 달았을 때 complete 1로 업데이트

	public List<QnAEnswerVO> enswerList(); // 답변 리스트를 뽑아서 답변을 했으면 답변완료 태그추가
	
	public List<QnAEnswerVO> enswerGetList(int replyNum); // 답변 리스트를 뽑아서 답변을 했으면 답변완료 태그추가
	
}
