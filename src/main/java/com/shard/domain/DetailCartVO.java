package com.shard.domain;

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
public class DetailCartVO {
	
	private int detCartNum;

	private int itemNum;
	
	private int cartItemCnt;
	
	private int cartNum;

	private String size;
	
	
}	
	
