package com.jubotech.business.web.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// AccountId
	private String account;
	private String password;
	private String nickname;
	private Integer type;// 账号类型（0:管理员，不能登录客户端；1：操作员，可登录客户端，运营后台暂时没有权限）（类型后续可扩展）
	private Integer cid;// SupplierId
	private Integer state;
	private Date create_time;
	
	
	public AccountInfo() {
	}

	
	public AccountInfo(String account, String password, String nickname, Integer type, Integer cid, Integer state) {
		this.account = account;
		this.password = password;
		this.nickname = nickname;
		this.type = type;
		this.cid = cid;
		this.state = state;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
