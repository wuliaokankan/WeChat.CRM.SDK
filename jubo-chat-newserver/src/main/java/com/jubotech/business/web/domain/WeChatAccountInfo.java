package com.jubotech.business.web.domain;

import java.io.Serializable;
import java.util.Date;

public class WeChatAccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String wechatid;
	private String wechatno;
	private String wechatnick;
	private Integer gender;
	private String avatar;
	private String country;
	private String province;
	private String city;
	private Integer isonline;//
	private Integer islogined;
	private String deviceid;
	private Integer cid;
	private Integer accountid;
	private Date login_time;
	private Date modify_time;
	 

	public WeChatAccountInfo() {
	}
 
	public WeChatAccountInfo(String wechatid, String wechatno, String wechatnick, Integer gender, String avatar,
			String country, String province, String city, Integer isonline, String deviceid, Integer cid,
			Integer accountid) {
		this.wechatid = wechatid;
		this.wechatno = wechatno;
		this.wechatnick = wechatnick;
		this.gender = gender;
		this.avatar = avatar;
		this.country = country;
		this.province = province;
		this.city = city;
		this.isonline = isonline;
		this.deviceid = deviceid;
		this.cid = cid;
		this.accountid = accountid;
	}

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

	public String getWechatnick() {
		return wechatnick;
	}

	public void setWechatnick(String wechatnick) {
		this.wechatnick = wechatnick;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

}
