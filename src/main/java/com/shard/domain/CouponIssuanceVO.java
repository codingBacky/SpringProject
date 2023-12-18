package com.shard.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class CouponIssuanceVO {

	private int issueNum;
	
	private Date issueDate;

	private Date issueED;
	
	private int couponNum;

	private String email;

	private Integer orderId;
}
