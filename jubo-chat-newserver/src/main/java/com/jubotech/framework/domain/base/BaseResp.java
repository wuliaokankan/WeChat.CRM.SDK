package com.jubotech.framework.domain.base;

import java.io.Serializable;
import java.util.Map;

import com.jubotech.framework.util.ActionResult;

/**
 * 
 * @author admin
 * 
 */
public class BaseResp implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer bizCode = ActionResult.BIZCODE_SUCCESS;

	private String msg = "success";

	private Map<String, Object> data;

	public BaseResp() {
		super();
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
