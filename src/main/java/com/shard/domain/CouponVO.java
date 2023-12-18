package com.shard.domain;

import java.sql.Date;
import java.util.ArrayList;
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
public class CouponVO {
	
	private int couponNum;
	
	private String couponName;
	
	private int discountRate;
	
	private int discountAmount;
	
	private Date expiryDate;
	
	private int minAmount;
	
}	
	
