package com.shard.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemReplyVO {

	private int replyNum;	//리뷰 번호
	
	private String replyTitle;	// 리뷰 제목
	
	private String replyContent; // 리뷰 내용
	
	private int starScore;	// 별점
	
	private Timestamp itemRepRegDate; // 리뷰 등록일
	
	private String img1;	// 리뷰 이미지
	
	private int itemNum; // 구매한제품 번호
	
	private String email;
}
