package com.jubotech.business.web.domain;

import java.io.Serializable;
import java.util.Date;

public class DeviceStateInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String wechatid;
	private String wechatno;
	private Integer isonline;
	private Integer islogined;
	private Integer cid;
	private String cname;
	private String deviceid;
	private Integer accountid;
	private String accountname;
	private Date login_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getWechatno() {
		return wechatno;
	}

	public void setWechatno(String wechatno) {
		this.wechatno = wechatno;
	}

	public Integer getIsonline() {
		return isonline;
	}

	public void setIsonline(Integer isonline) {
		this.isonline = isonline;
	}

	public Integer getIslogined() {
		return islogined;
	}

	public void setIslogined(Integer islogined) {
		this.islogined = islogined;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

}
