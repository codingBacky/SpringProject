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
public class DetailOrderVO {
	
	private int detOrdNUm;
	
	private int itemNum;
	
	private int cnt;
	
	private int orderId;
	
	private String size;
	
}	
	
