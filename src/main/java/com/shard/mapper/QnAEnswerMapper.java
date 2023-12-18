package com.shard.mapper;

import java.util.List;

import com.shard.domain.QnAEnswerVO;

public interface QnAEnswerMapper {

	public int insertEnswer(QnAEnswerVO vo);
	
	public int updateComplete(int replyNum);
	
	public List<QnAEnswerVO> enswerList();
	
	public List<QnAEnswerVO> enswerGetList(int replyNum);
	
}