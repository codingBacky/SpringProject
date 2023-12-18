package com.shard.domain;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

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
public class QnAVO {
	private int replyNum;
	
	private String replyTitle;
	
	private String replyContent;
	
	private String replyImg;
	
	private String replyPwd;
	
	private Timestamp replyRegDate;
	
	private int replyComplete;
	
	private String email;
	
	private String replyCategory;
	
	private int inquiryNum;
	
	private int itemNum;
}
