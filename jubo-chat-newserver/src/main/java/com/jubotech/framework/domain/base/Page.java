package com.jubotech.framework.domain.base;

public class Page {

	private Integer pageSize = 10;// 每页多少条

	private Integer pageNo = 1;// 当前第几页

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (null == pageSize) {
			pageSize = 10;
		}
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if (null == pageNo) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
	}

}
