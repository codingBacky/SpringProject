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
public class DeliverAddrVO {
	
	private int addrNum;
	
	private String addrName;
	
	private int postcode;
	
	private String roadAddress;
	
	private String detailAddr;
	
	private String email;
	
	private int defaultWhether;
	
}