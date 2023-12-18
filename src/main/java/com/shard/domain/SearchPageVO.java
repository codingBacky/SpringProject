package com.shard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchPageVO {
	private int startPage, endPage, pageNum, total, start;

	private String itemName = "";

	private int categoryNum = 0;

	private int amount = 40;
	
	private boolean prev, next;

	public SearchPageVO(int pageNum, int total) {
		this.pageNum = pageNum;
		this.total = total;
		this.start = (this.pageNum - 1) * this.amount;

		this.endPage = (int) Math.ceil(pageNum / 10.0) * 10;
		this.startPage = this.endPage - 9;

		int realEnd = (int) Math.ceil((total * 1.0) / amount);

		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}

		this.prev = this.startPage > 1;

		this.next = this.endPage < realEnd;
	}
}