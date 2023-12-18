package com.shard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shard.domain.QnAEnswerVO;
import com.shard.mapper.QnAEnswerMapper;

@Service
public class QnAEnswerServiceImpl implements QnAEnswerService{
	
	@Autowired
	private QnAEnswerMapper mapper;


	@Override
	public int insertEnswer(QnAEnswerVO vo) {
		return mapper.insertEnswer(vo);
	}

	@Override
	public int updateComplete(int replyNum) {
		return mapper.updateComplete(replyNum);
	}

	@Override
	public List<QnAEnswerVO> enswerList() {
		return mapper.enswerList();
	}

	@Override
	public List<QnAEnswerVO> enswerGetList(int replyNum) {
		return mapper.enswerGetList(replyNum);
	}


}
