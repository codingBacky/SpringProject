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
@NoArgsConstructor
@AllArgsConstructor
public class ItemVO {
	private int itemNum;

	private String itemName;

	private int sale;

	private int itemCountM;

	private int itemCountL;

	private int itemCountXL;

	private Timestamp itemRegDate;

	private String mainImg;

	private String subImg1;

	private String subImg2;

	private String subImg3;

	private String subImg4;

	private int readCount;

	private int categoryNum;

	private List<ItemVO> itemList;

	public List<ItemVO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemVO> itemList) {
		this.itemList = itemList;
	}
}