package com.shard.domain;

import java.sql.Date;

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
public class PayVO {

	private String payId;
	
	private int orderId;
	
	private String email;
	
	private int payTotal;
	
	private String payMethod;
	
	private int payComplete;
	
	private Date payDate;
}	
	
