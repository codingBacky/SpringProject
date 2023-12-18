package com.shard.mapper;

import java.util.List;

import com.shard.domain.PageVO;
import com.shard.domain.QnAVO;

public interface QnAMapper {
	public List<QnAVO> qnaList(PageVO vo);

	public int qnaInsert(QnAVO vo);

	public int qnaReInsert(QnAVO vo);

	public int delete(int replyNum);

	public int totalCount();

	public String getReplyPwd(int replyNum);

	public List<QnAVO> getQnAList(int replyNum);

	public QnAVO getQnA(int replyNum);

	public int lastId();

	public void inquiryNumUpadte(int lastId);

	public List<QnAVO> myPageQnAList(String email);

	public int myPageQnACount(String email);
}