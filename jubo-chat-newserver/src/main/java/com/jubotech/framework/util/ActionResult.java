package com.jubotech.framework.util;

/**
 * @author Wander.Zeng
 * @data 2015-10-25 下午9:34:53
 * @desc ActionResult.java
 */
public class ActionResult {
	
    /**服务端正确返回*/
	public static final int BIZCODE_SUCCESS = 0;
	
	/**服务端错误返回*/
	public static final int BIZCODE_ERROR = 1;

	private Integer bizCode;
	private String bizMsg;

	public ActionResult() {
		setResult(BIZCODE_SUCCESS, null);
	}

	public void setResultError(String bizMsg) {
		setResult(BIZCODE_ERROR, bizMsg);
	}

	public void setResult(Integer bizCode, String bizMsg) {
		this.bizCode = bizCode;
		this.bizMsg = bizMsg;
	}

	public Integer getBizCode() {
		return bizCode;
	}

	public void setBizCode(Integer bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizMsg() {
		return bizMsg;
	}

	public void setBizMsg(String bizMsg) {
		this.bizMsg = bizMsg;
	}

}
