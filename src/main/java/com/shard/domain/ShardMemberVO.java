package com.shard.domain;

import java.sql.Timestamp;
import java.util.List;

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
public class ShardMemberVO {
	private String userPwd;
	
	private String userName;
	
	private String email;
	
	private String phone;
	
	private Timestamp dob;
	
	private int secession;
	
	private int point;
	
	private Timestamp userRegiDate;
	
	private int memNum;
	
	private String authCode;
	
	private String gender;
	
	private List<AuthorityVO> authList;
	
}	
	
