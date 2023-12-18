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
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {
	private int noticeNum;
	
	private String noticeTitle;
	
	private String noticeContent;
	
	private Timestamp noticeRegiDate;
	
	private String noticeImg;
}
