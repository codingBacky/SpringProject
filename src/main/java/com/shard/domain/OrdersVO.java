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
public class OrdersVO {
	private int orderId;
	private int totalPrice;
	private Date ordDate;
	private String req;
	private int deliverZipCode;
	private String userDeliverAddr;
	private int usePoint;
	private String email;
	private String deliverDetailAddr;
	private String deliverName;
	private String deliverPhone;
	private int orderComplete;
}
