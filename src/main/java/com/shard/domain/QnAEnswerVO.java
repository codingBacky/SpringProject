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
public class QnAEnswerVO {
	
	private int enswerNum;
	
	private int replyNum;
	
	private String enswerContent;
	
	private Timestamp enswerRegDate;
	
	private String email;
	
	private int re_inquiryNum;
	
	private int inquiryNum;
}
