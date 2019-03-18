package com.jubotech.framework.domain.base;

public class DBPage {
	// 数据库用
	private Integer limit;

	private Integer offset;

	public Integer getLimit() {
		return limit;
	}

	public DBPage() {
	}

	public DBPage(Integer limit, Integer offset) {
		super();
		this.limit = limit;
		this.offset = offset;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

}
