package com.jubotech.business.web.domain.req;

import com.jubotech.framework.domain.base.Page;

public class PhoneVo extends Page {

	private String deviceid;

	private Integer cid;
	
	private Integer accountid;// 所属操作员账号id 

	private String nickname;

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

}
