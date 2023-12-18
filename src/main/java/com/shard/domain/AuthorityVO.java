package com.shard.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class AuthorityVO {
	private String authCode;
	
	private String authName;
	
	private String authDetail;
}
