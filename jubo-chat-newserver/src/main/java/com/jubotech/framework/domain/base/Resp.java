package com.jubotech.framework.domain.base;

import java.util.Map;

import com.jubotech.framework.common.ActionResult;

/**
 * 全局返回封装类
 * 
 * @author lenovo
 */
public class Resp {

	private Integer bizCode = ActionResult.BIZCODE_SUCCESS;

	private String msg = ActionResult.SUCCESS_MSG;

	private Map<String, Object> data;

	public Resp() {
		super();
	}

	public Resp(Map<String, Object> data) {
		super();
		this.data = data;
	}

	public Resp(Integer bizCode, String msg, Map<String, Object> data) {
		super();
		this.bizCode = bizCode;
		this.msg = msg;
		this.data = data;
	}

	public Integer getBizCode() {
		return bizCode;
	}

	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
