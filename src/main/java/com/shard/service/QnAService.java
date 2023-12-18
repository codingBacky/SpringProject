package com.shard.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shard.domain.PageVO;
import com.shard.domain.QnAVO;

public interface QnAService {
	public List<QnAVO> qnaList(PageVO vo);
	
	public int qnaInsert(QnAVO vo, MultipartFile file);
	
	public int qnaInsert(QnAVO vo);
	
	public int qnaReInsert(QnAVO vo);
	
	public int delete(int replyNum);
	
	public List<QnAVO> getQnAList(int replyNum);
	
	public int totalCount();
	
	public int getReplyCheck(int replyNum, String replyPwd);
	
	public QnAVO getQnA(int replyNum);
	
	// 마이페이지 들어갈 자신의 게시글
	public List<QnAVO> myPageQnAList(String email);
	
	public int myPageQnACount(String email);
}