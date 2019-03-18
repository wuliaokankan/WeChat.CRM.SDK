package com.jubotech.framework.domain.base;

import java.util.List;

public class PageBean<T> {
	private Integer pageSize;//每页多少条

	private Integer pageNo;//当前第几页

	private Integer pageTotal;//总共多少页

	private List<T> resultList;
	 
	public PageBean(Integer pageSize, Integer pageNo, Integer pageTotal, List<T> resultList) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.pageTotal = pageTotal;
		this.resultList = resultList;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

}
